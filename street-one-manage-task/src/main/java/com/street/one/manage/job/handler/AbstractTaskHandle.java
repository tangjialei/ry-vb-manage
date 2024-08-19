package com.street.one.manage.job.handler;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.enums.TaskCodeEnum;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.common.utils.DateUtil;
import com.github.pagehelper.util.StringUtil;
import com.street.one.manage.middleware.zd.service.IZdReceivingAlarmMainDetailsService;
import com.street.one.manage.middleware.zd.service.IZdTempReceivingAlarmMainDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.handler
 * @ClassName: AbstractTaskHandle
 * @Author: tjl
 * @Description: 抽象的计划任务处理类
 * @Date: 2024/7/5 15:15
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public abstract class AbstractTaskHandle {

    /***
     * 分页大小
     */
    public static final  int PAGE_SIZE = 100;

    /***
     * 获取头部认证参数
     * @return
     */
    protected abstract Map<String, String> getHeaders();

    /****
     * 请求标准接口
     * @return
     */
    protected abstract String getApiActionName();

    @Autowired
    protected IZdReceivingAlarmMainDetailsService zdReceivingAlarmMainDetailsService;

    @Autowired
    protected IZdTempReceivingAlarmMainDetailService zdTempReceivingAlarmMainDetailService;

    /***
     * get请求构建参数
     * @return
     */
    protected  String getGetMethodBuildParam(int pageNo, Date startPullTimeDate, Date actualLastTime){
        return "&page_num=" + pageNo +
                "&page_size=" + PAGE_SIZE +
                "&sjc=" + DateUtil.format(startPullTimeDate, DateUtil.DEF_DATETIME_FMT) +
                "&opsjc=" + DateUtil.format(actualLastTime, DateUtil.DEF_DATETIME_FMT);
    }


    /***
     * 拉取第三方数据
     * @param pageNo 页码，子类维护的，所以只有传入
     * @param methodType
     * //@param jobId 计划任务id,basis_job表中配置的
     * @param startPullTimeDate
     * @param actualLastTime
     * @return
     */
    protected Object taskPullThirdData(int pageNo, String methodType,Date startPullTimeDate, Date actualLastTime) throws TaskException {
        String apiActionName = getApiActionName();
        if(StringUtil.isEmpty(apiActionName)){
            throw new TaskException("拉取第三方接口地址不能为空", TaskCodeEnum.CONFIG_ERROR);
        }
        String filter;
        //get方法
        if(methodType.equals(CommonConstants.METHOD_TYPE)){
            //获取get方法的请求参数
            filter = getGetMethodBuildParam(pageNo,startPullTimeDate, actualLastTime);
            apiActionName+=filter;
        }else if(methodType.equals(CommonConstants.POST_METHOD)){
            System.out.println("post请求暂时不实现");
        }
        Map<String, String> headers = getHeaders();
        if(CollectionUtils.isEmpty(headers)){
            throw new TaskException("请求头认证参数不能为空", TaskCodeEnum.CONFIG_ERROR);
        }
        HttpResponse execute = HttpUtil.createGet(apiActionName).addHeaders(headers).execute(false);
        if (CommonConstants.HTTP_CODE != execute.getStatus()) {
            throw new TaskException("第三方接口调用失败!", TaskCodeEnum.TASK_EXECUTION_FAILURE);
        }
        String body = execute.body();
        //请求第三方,返回结果
        if(StringUtil.isEmpty(body)){
            throw new TaskException("第三方返回结果集为空", TaskCodeEnum.TASK_EXECUTION_FAILURE);
        }
        //响应消息
        JSONObject response = JSONObject.parseObject(body);
        int status = response.getIntValue("status");
        //不是成功状态
        if(CommonConstants.HTTP_CODE != status){
            throw new TaskException(response.getString("message"), TaskCodeEnum.TASK_EXECUTION_FAILURE);
        }
        return body;
    }


}
