package com.street.one.manage.common.core.domain.callback;

import com.street.one.manage.common.core.domain.WebSocketCallbackResult;
import com.street.one.manage.common.enums.BusinessType;
import com.street.one.manage.common.enums.LogStatus;

import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.service
 * @ClassName: WebSocketClientCallback
 * @Author: tjl
 * @Description: 客户端回调消息
 * @Date: 2024/7/4 11:11
 * @modified modify person name
 * @Version: 1.0
 */
public interface IWebSocketClientCallback {

    /***
     * 处理服务器返回的消息
     * @param response
     * @param ip
     * @return
     */
    WebSocketCallbackResult onMessage(String response, String ip);

    /***
     * 记录操作日志
     * @param code onMessage 返回的code
     * @param businessType （0=其它,1=新增,2=修改,3=删除,4=导出,5=导入,6=强退,7=拉取）
     * @param ip 请求websocket server 的ip
     * @param executeStatus  执行状态
     * @param response  websocket 服务器返回的参数
     * @param errorMsg  内部异常信息
     */
    void recordsReceivingAlarmLog(String code, BusinessType businessType, String ip,
                                  LogStatus executeStatus,String response,String errorMsg);


    /***
     * 保存数据
     * @param records
     */
    void saveDate(List<?> records);


    /***
     * 处理错误
     * @param e
     */
    void onError(Exception e);

}
