package com.street.one.manage.job.task;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.common.utils.DateUtil;
import com.street.one.manage.common.utils.JSONUtils;
import com.street.one.manage.common.utils.SequenceUtils;
import com.street.one.manage.framework.utils.DataSourceContextUtils;
import com.street.one.manage.job.adapter.ReceivingAlarmMainInfoAdapter;
import com.street.one.manage.job.handler.AbstractTaskHandle;
import com.street.one.manage.common.core.domain.model.AbstractInfo;
import com.street.one.manage.job.thirdmode.ThirdConvertInfo;
import com.street.one.manage.job.thirdmode.ZdApiResult;
import com.street.one.manage.job.utils.TaskTimeUtils;
import com.google.common.collect.Maps;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmMainDetailEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.task
 * @ClassName: ReceivingAlarmMainInfoTask
 * @Author: tjl
 * @Description: 接处警_案件基本信息详情-数据来源总队接口,计划任务拉取
 * @Date: 2024/7/5 14:58
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
@Component("receivingAlarmMainInfoTask")
@RequiredArgsConstructor
public class ReceivingAlarmMainInfoTask extends AbstractTaskHandle {

    /***
     * 接处警_案件基本信息详情 适配器处理
     */
    private final ReceivingAlarmMainInfoAdapter adapter;

    /***
     * 按天纬度,每隔30秒执行一次 接处警_案件基本信息详情 拉取
     * @param jobId  计划任务id
     * @throws TaskException
     */
    public void receivingAlarmMainInfoJob(Integer jobId) throws TaskException {
        int pageNo = 1;
        log.info("===================receivingAlarmMainInfoJob start=================== ");
        //是否跳过本次拉取
        Boolean isContinue = true;
        //获取开始时间
        Date startPullDateTime = TaskTimeUtils.getStartPullTime(jobId);
        //你拉的开始时间都大于现在拉就没必要拉了
        if (startPullDateTime.after(DateUtil.getNow())) {
            log.info("pull time > current time ::{}",DateUtil.format(startPullDateTime));
            return;
        }
        //凌晨开始拉取
        startPullDateTime = DateUtil.convertToDate(DateUtil.format(startPullDateTime,
                DateUtil.DEF_DATE_FMT) + " 00:00:00");

        //结束时间,时间间隔为一天
        Date lastTime = DateUtil.adjust(startPullDateTime, Calendar.MONTH, 1);
        //最终时间不等于上一次请求时间+时间间隔 考虑到最后时间不能大于当前系统时间
        Date actualLastTime = lastTime.after(DateUtil.getNow()) ? DateUtil.getNow() : lastTime;
        Long totalCount = null;
        while (totalCount == null || totalCount > (pageNo - 1) * PAGE_SIZE) {
            //拉取第三方数据
            Object result = taskPullThirdData(pageNo, CommonConstants.METHOD_TYPE, startPullDateTime, actualLastTime);
            if(null == result){
                //终止本次查询
                isContinue = false;
                break;
            }
            ZdApiResult zdApiResult = JSONUtils.json2Bean(String.valueOf(result),ZdApiResult.class);
            if(null == zdApiResult){
                //终止本次查询
                isContinue = false;
                break;
            }
            //总数据大小如果为零不进行后续逻辑处理了,没有必要
            totalCount = new JSONObject(zdApiResult.getData()).getJSONObject("result").getLong("total_size");
            if(null == totalCount ||  0 == totalCount){
                break;
            }
            //数据转换
            ThirdConvertInfo thirdConvertInfo = adapter.convertMiddleInfo(zdApiResult);
            if(!thirdConvertInfo.getSuccess()){
                //终止本次查询
                isContinue = false;
                break;
            }
            //获取转换成功的数据,保存数据
            List<AbstractInfo> successInfos = thirdConvertInfo.getSuccessInfos();
            if(CollectionUtils.isEmpty(successInfos)){
                //终止本次查询
                isContinue = false;
                break;
            }
            //保存内部数据库对象
            List<ZdReceivingAlarmMainDetailEntity> saveData = Lists.newArrayList();
            //内部数据库警情详情
            Map<String, ZdReceivingAlarmMainDetailEntity> receivingAlarmMainDetailsEntityHashMap = Maps.newHashMap();
            //先查询数据库
            List<String> thirdIds = successInfos.stream().map(t -> ((ZdReceivingAlarmMainDetailEntity) t).getThirdId()).collect(Collectors.toList());
            //切换数据源
            DataSourceContextUtils.changeZdBusinessDatasourceId();
            List<ZdReceivingAlarmMainDetailEntity> list = zdReceivingAlarmMainDetailsService.list(new LambdaQueryWrapper<ZdReceivingAlarmMainDetailEntity>()
                    .in(ZdReceivingAlarmMainDetailEntity::getThirdId, thirdIds).orderByDesc(ZdReceivingAlarmMainDetailEntity::getRegisterTime));
            if(!CollectionUtils.isEmpty(list)){
                receivingAlarmMainDetailsEntityHashMap = list.stream().collect(Collectors.toMap(ZdReceivingAlarmMainDetailEntity::getThirdId, t->t));
            }
            //组装保存对象
            for (AbstractInfo successInfo : successInfos) {
                //数据库不存在的对象
                ZdReceivingAlarmMainDetailEntity receivingAlarmMainDetail = (ZdReceivingAlarmMainDetailEntity) successInfo;
                //数据库已存在的对象
                ZdReceivingAlarmMainDetailEntity info = receivingAlarmMainDetailsEntityHashMap.get(receivingAlarmMainDetail.getThirdId());
                if(null != info){
                    //code先取出来,copyProperties 可能会把code赋值null,因为receivingAlarmMainDetail没有生成code
                    String code = info.getCode();
                    Integer id = info.getId();
                    BeanUtils.copyProperties(receivingAlarmMainDetail,info);
                    info.setCode(code);
                    info.setId(id);
                    saveData.add(info);
                }else{
                    //生成序列号code,全局唯一
                    receivingAlarmMainDetail.setCode(SequenceUtils.generatorSeq());
                    saveData.add(receivingAlarmMainDetail);
                }

            }
            //存在则更新,不存在则插入
            if(!CollectionUtils.isEmpty(saveData)){
                zdReceivingAlarmMainDetailsService.saveOrUpdateBatch(saveData);
                log.info("successfully....");
                DataSourceContextUtils.clearDataSourceKey();
            }
            pageNo += 1;
        }
        //是否终止本次拉取
        if (isContinue) {
            //最后拉取时间
            TaskTimeUtils.setTaskPullTime(jobId,actualLastTime,1,Calendar.DATE);
        }
        log.info("===================receivingAlarmMainInfoJob end=================== ");
    }


    @Override
    protected Map<String, String> getHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("content-type", "application/json");
        headers.put("Authorization", "Bearer eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiIsInVjIjoiYWRmZmJmZTFhZDhjMzhjYTUwMDY3YjM2MTNmMmJlMDgiLCJ0b2tlbl90eXBlIjoxfQ.eyJpc3MiOiJnQjhncnZFaXE4TENuU3dESVIweEtuZG9MYW90Q0ZzRCIsImlhdCI6MTY3OTIzODQxMH0.UZ8_sMjKN0L4bmCLX-9bmJiG2oWV6H24S5pAKL_j8Mk");
        return headers;
    }

    @Override
    protected String getApiActionName() {
        return "http://10.81.69.144:8000/dapi/shxf/jcj/ajxxx?sjy=287bb3bfe911b7d014db308504232141";
    }


    @Override
    protected String getGetMethodBuildParam(int pageNo, Date startPullTimeDate, Date actualLastTime) {
        return "&page_num=" + pageNo +
                "&page_size=" + PAGE_SIZE + "&shijc="  + DateUtil.format(startPullTimeDate, DateUtil.DEF_DATETIME_FMT) +
                "&opshijc=" + DateUtil.format(actualLastTime, DateUtil.DEF_DATETIME_FMT);
    }
}

