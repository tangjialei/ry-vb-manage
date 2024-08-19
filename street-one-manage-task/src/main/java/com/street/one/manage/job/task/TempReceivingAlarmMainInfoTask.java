package com.street.one.manage.job.task;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.model.AbstractInfo;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.common.utils.DateUtil;
import com.street.one.manage.common.utils.JSONUtils;
import com.street.one.manage.common.utils.SequenceUtils;
import com.street.one.manage.framework.utils.DataSourceContextUtils;
import com.street.one.manage.job.adapter.TempReceivingAlarmMainInfoAdapter;
import com.street.one.manage.job.handler.AbstractTaskHandle;
import com.street.one.manage.job.thirdmode.ThirdConvertInfo;
import com.street.one.manage.job.thirdmode.ZdApiResult;
import com.street.one.manage.middleware.zd.entity.ZdTempReceivingAlarmMainDetailEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.task
 * @ClassName: TempReceivingAlarmMainInfoTask
 * @Author: tjl
 * @Description: 接处警_案件基本信息详情-数据来源总队接口,（T -2） 模式计划任务拉取
 * @Date: 2024/7/11 10:40
 * @modified modify person name
 * @Version: 1.0
 */
@Component("tempReceivingAlarmMainInfoTask")
@RequiredArgsConstructor
@Slf4j
public class TempReceivingAlarmMainInfoTask extends AbstractTaskHandle {

    /***
     * 接处警_案件基本信息详情 适配器处理
     */
    private final TempReceivingAlarmMainInfoAdapter adapter;

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


    /***
     * 拉取总队接口详情(t-2)天的数据
     * @throws TaskException
     */
    public void pullReceivingAlarmMainInfoTwoDaysJob() throws TaskException {
        int pageNo = 1;
        log.info("===================two days receivingAlarmMainInfoJob start=================== ");
        //统一时间起点
        Date now = DateUtil.getNow();
        //调整时间为(t -2)
        Date startPullDateTime = DateUtil.convertToDate(DateUtil.format(DateUtil.adjust(now, Calendar.DATE, -2),
                DateUtil.DEF_DATE_FMT) + " 00:00:00");
        System.out.println(DateUtil.format(startPullDateTime));
        //结束时间,时间间隔为两天
        Date lastTime = DateUtil.convertToDate(DateUtil.format(now,
                DateUtil.DEF_DATE_FMT) + " 23:59:59");
        if(null == lastTime){
            //结束时间有问题就没必要继续拉取了，只能人工干预，一般不会出现这种情况，这里做个容错
            return;
        }
        //最终时间不等于上一次请求时间+时间间隔 考虑到最后时间不能大于当前系统时间
        Date actualLastTime = lastTime.after(now) ? now : lastTime;
        Long totalCount = null;
        while (totalCount == null || totalCount > (pageNo - 1) * PAGE_SIZE) {
            //拉取第三方数据
            Object result = taskPullThirdData(pageNo, CommonConstants.METHOD_TYPE, startPullDateTime, actualLastTime);
            if(null == result){
                break;
            }

            ZdApiResult zdApiResult = JSONUtils.json2Bean(String.valueOf(result),ZdApiResult.class);
            if(null == zdApiResult){
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
                break;
            }

            //获取转换成功的数据,保存数据
            List<AbstractInfo> successInfos = thirdConvertInfo.getSuccessInfos();
            if(CollectionUtils.isEmpty(successInfos)){
                break;
            }
            //保存内部数据库对象
            List<ZdTempReceivingAlarmMainDetailEntity> saveData = Lists.newArrayList();
            //内部数据库警情详情
            Map<String, ZdTempReceivingAlarmMainDetailEntity> tempReceivingAlarmMainDetailHashMap = Maps.newHashMap();
            //先查询数据库
            List<String> thirdIds = successInfos.stream().map(t -> ((ZdTempReceivingAlarmMainDetailEntity) t).getThirdId()).collect(Collectors.toList());
            //切换数据源
            DataSourceContextUtils.changeZdBusinessDatasourceId();
            List<ZdTempReceivingAlarmMainDetailEntity> list = zdTempReceivingAlarmMainDetailService.list(new LambdaQueryWrapper<ZdTempReceivingAlarmMainDetailEntity>()
                    .in(ZdTempReceivingAlarmMainDetailEntity::getThirdId, thirdIds).orderByDesc(ZdTempReceivingAlarmMainDetailEntity::getRegisterTime));
            if(!CollectionUtils.isEmpty(list)){
                tempReceivingAlarmMainDetailHashMap = list.stream().collect(Collectors.toMap(ZdTempReceivingAlarmMainDetailEntity::getThirdId, t->t));
            }
            //组装保存对象
            for (AbstractInfo successInfo : successInfos) {
                //数据库不存在的对象
                ZdTempReceivingAlarmMainDetailEntity tempReceivingAlarmMainDetail = (ZdTempReceivingAlarmMainDetailEntity) successInfo;
                //数据库已存在的对象
                ZdTempReceivingAlarmMainDetailEntity info = tempReceivingAlarmMainDetailHashMap.get(tempReceivingAlarmMainDetail.getThirdId());
                if(null != info){
                    //code先取出来,copyProperties 可能会把code赋值null,因为receivingAlarmMainDetail没有生成code
                    Integer id = info.getId();
                    String code = info.getCode();
                    BeanUtils.copyProperties(tempReceivingAlarmMainDetail,info);
                    //修改同步标记
                    info.setIsSync(false);
                    info.setCode(code);
                    info.setId(id);
                    saveData.add(info);
                }else{
                    //生成序列号code,全局唯一
                    tempReceivingAlarmMainDetail.setCode(SequenceUtils.generatorSeq());
                    saveData.add(tempReceivingAlarmMainDetail);
                }

            }
            //存在则更新,不存在则插入
            if(!CollectionUtils.isEmpty(saveData)){
                zdTempReceivingAlarmMainDetailService.saveOrUpdateBatch(saveData);
                log.info("successfully....");
                DataSourceContextUtils.clearDataSourceKey();
            }
            pageNo += 1;
        }
        log.info("===================receivingAlarmMainInfoJob end=================== ");
    }
}
