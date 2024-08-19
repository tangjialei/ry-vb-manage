package com.street.one.manage.common.core.domain.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.constants.ThirdConfigConstants;
import com.street.one.manage.common.core.domain.WebSocketCallbackResult;
import com.street.one.manage.common.core.domain.callback.IWebSocketClientCallback;
import com.street.one.manage.common.enums.BusinessType;
import com.street.one.manage.common.enums.LogStatus;
import com.street.one.manage.common.utils.ExceptionUtil;
import com.street.one.manage.common.utils.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.TimerTask;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.factory
 * @ClassName: WebSocketClientFactory
 * @Author: tjl
 * @Description: WebSocketClient 客户端管理类
 * @Date: 2024/7/4 11:12
 * @modified modify person name
 * @Version: 1.0
 */
@Component
@Slf4j
public class WebSocketClientManager {

    /***
     * WebSocketClient 客户端
     */
    private WebSocketClient webSocketClient;


    /***
     * 创建 WebSocketClient 客户端
     * @param callback 接口回调消息，需要实现IWebSocketClientCallback 接口
     * @return
     * @throws URISyntaxException
     */
    private WebSocketClient createWebSocketClient(IWebSocketClientCallback callback) throws URISyntaxException {
        String ip = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_WEB_SOCKET_SERVER);
        WebSocketClient webSocketClient = new WebSocketClient(new URI(ip)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                log.info("连接上服务器了 WebSocketServer ip:{}",ip);
                //默认消息，可不发送,这里可以做认证
                JSONObject msg = new JSONObject();
                msg.put("messageType","init-xhxf-street-one-manage-connection");
                this.send(JSON.toJSONString(msg));
            }

            @Override
            public void onMessage(String msg) {
                if(StringUtil.isEmptyOrNull(msg)){
                    //丢弃消息，不做处理
                    return;
                }
                //调用内部处理逻辑
                WebSocketCallbackResult webSocketCallbackResult = callback.onMessage(msg, ip);
                //只记录SM03 的消息日志,其它消息这里不处理
                if(null != webSocketCallbackResult){
                    //内部处理失败记录日志
                    if (!webSocketCallbackResult.getSuccess()) {
                        //记录失败日志
                        callback.recordsReceivingAlarmLog(webSocketCallbackResult.getCode(), BusinessType.INSERT,ip,
                                LogStatus.ERROR,msg,webSocketCallbackResult.getMsg());
                    } else{
                        try{
                            //保存具体数据
                            callback.saveDate(webSocketCallbackResult.getValues());
                            //记录成功日志
                            callback.recordsReceivingAlarmLog(webSocketCallbackResult.getCode(), BusinessType.INSERT,ip,
                                    LogStatus.SUCCESS,msg,null);
                        }catch (Exception e){
                            String errorMsg = StringUtil.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
                            //记录失败日志
                            callback.recordsReceivingAlarmLog(webSocketCallbackResult.getCode(), BusinessType.INSERT,ip,
                                    LogStatus.ERROR,msg,errorMsg);
                        }
                    }
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                //异步重连
                AsyncManager.asyncManager().execute(restConnectWebSocketServer(callback));
            }

            @Override
            public void onError(Exception e) {
                //异常消息内部处理
                callback.onError(e);
            }
        };
        //连接websocket 服务器
        webSocketClient.connect();
        return webSocketClient;
    }


    /***
     * 启动 WebSocketClient 客户端
     * @return
     */
    public synchronized void connectWebSocketServer(IWebSocketClientCallback callback) throws URISyntaxException {
        // 关闭旧的websocket连接, 避免占用资源
        if(null != webSocketClient){
            webSocketClient.close();
        }
        log.info("打开新的websocket连接");
        //创建 WebSocketClient 客户端
        webSocketClient = this.createWebSocketClient(callback);
    }

    /***
     * 延时重连
     * @param callback
     * @return
     */
    public TimerTask restConnectWebSocketServer(IWebSocketClientCallback callback) {
        return new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                connectWebSocketServer(callback);
            }
        };
    }


}
