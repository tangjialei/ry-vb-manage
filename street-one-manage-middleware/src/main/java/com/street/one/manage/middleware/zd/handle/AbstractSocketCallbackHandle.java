package com.street.one.manage.middleware.zd.handle;

import com.street.one.manage.common.core.domain.manager.AsyncManager;
import com.street.one.manage.common.core.domain.manager.WebSocketClientManager;
import com.street.one.manage.common.enums.BusinessType;
import com.street.one.manage.common.enums.LogStatus;
import com.street.one.manage.common.utils.IpUtils;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.framework.utils.DataSourceContextUtils;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmLogEntity;
import com.street.one.manage.middleware.zd.service.IZdReceivingAlarmLogService;
import com.street.one.manage.middleware.zd.service.IZdReceivingAlarmMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.handle
 * @ClassName: AbstractExcelHandle
 * @Author: tjl
 * @Description: websocket 回调抽象类
 * @Date: 2024/7/4 17:07
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public abstract class AbstractSocketCallbackHandle {

    /** websocket 客户端工厂类***/
    @Autowired
    protected  WebSocketClientManager webSocketClientFactory;
    /***接处警信息服务***/
    @Autowired
    protected  IZdReceivingAlarmMainService zdReceivingAlarmMainService;

    /***
     * 记录操作日志
     * @param code onMessage 返回的code
     * @param businessType （0=其它,1=新增,2=修改,3=删除,4=导出,5=导入,6=强退,7=拉取）
     * @param ip 请求websocket server 的ip
     * @param executeStatus  执行状态
     * @param response  websocket 服务器返回的参数
     * @param errorMsg  内部异常信息
     */
    protected void writeReceivingAlarmLog(String code, BusinessType businessType,
                                          String ip, LogStatus executeStatus,String response,
                                          String errorMsg){
        ZdReceivingAlarmLogEntity zdReceivingAlarmLogEntity = new ZdReceivingAlarmLogEntity();
        zdReceivingAlarmLogEntity.setCode(code);
        zdReceivingAlarmLogEntity.setBusinessType(businessType.ordinal());
        if(!StringUtil.isEmptyOrNull(ip)){
            zdReceivingAlarmLogEntity.setUrl(ip);
            zdReceivingAlarmLogEntity.setIp(IpUtils.extractWebSocketIp(ip));
            zdReceivingAlarmLogEntity.setMethod(IpUtils.extractWebSocketMethod(ip));
        }
        zdReceivingAlarmLogEntity.setRequestMethod("ws");
        zdReceivingAlarmLogEntity.setResponseParam(response);
        zdReceivingAlarmLogEntity.setExecuteStatus(executeStatus.getType());
        zdReceivingAlarmLogEntity.setErrorMsg(errorMsg);
        zdReceivingAlarmLogEntity.setCreateTime(LocalDateTime.now());
        //异步记录日志
        AsyncManager.asyncManager().execute(recordReceivingAlarmPushLog(zdReceivingAlarmLogEntity));
    }

    /***
     * 记录数据中台推送警情数据日志
     * @param zdReceivingAlarmLogEntity 日志对象
     * @return TimerTask
     */
    public static TimerTask recordReceivingAlarmPushLog(final ZdReceivingAlarmLogEntity zdReceivingAlarmLogEntity){
       return new TimerTask() {
           @Override
           public void run() {
               //切换数据源,运营平台B库
               DataSourceContextUtils.changeZdBusinessDatasourceId();
               SpringApplicationContext.getBean(IZdReceivingAlarmLogService.class).save(zdReceivingAlarmLogEntity);
               DataSourceContextUtils.clearDataSourceKey();
               log.info("异步数据中台推送警情数据日志记录成功!");
           }
       };
    }
}
