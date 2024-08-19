package com.street.one.manage.middleware.zd.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.WebSocketCallbackResult;
import com.street.one.manage.common.core.domain.callback.IWebSocketClientCallback;
import com.street.one.manage.common.enums.BusinessType;
import com.street.one.manage.common.enums.LogStatus;
import com.street.one.manage.common.utils.ExceptionUtil;
import com.street.one.manage.common.utils.SequenceUtils;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.framework.utils.DataSourceContextUtils;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmMainEntity;
import com.street.one.manage.middleware.zd.handle.AbstractSocketCallbackHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.service.impl
 * @ClassName: WebSocketClientCallbackService
 * @Author: tjl
 * @Description: websocket消息回调服务
 * @Date: 2024/7/4 13:23
 * @modified modify person name
 * @Version: 1.0
 */
@Service
@Slf4j
public class WebSocketCallbackServiceImpl extends AbstractSocketCallbackHandle implements IWebSocketClientCallback , ApplicationRunner {


    /***
     * 项目启动，连接中台 websocket服务
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        webSocketClientFactory.connectWebSocketServer(this);
    }

    @Override
    public WebSocketCallbackResult onMessage(String response, String ip) {
        JSONObject data = JSON.parseObject(response);
        //消息类型，与中台统一定义的
        String messageType = data.getString("messageType");
        //为空丢弃本次操作
        if(StringUtil.isEmptyOrNull(messageType) || !messageType.equals(CommonConstants.SOCKET_MESSAGE_TYPE_03)){
            return null;
        }
        //异步记录init日志
        String msgCode = SequenceUtils.generatorSeq();
        this.recordsReceivingAlarmLog(msgCode,BusinessType.INSERT,ip,LogStatus.INIT,response,null);
        List<ZdReceivingAlarmMainEntity> zdReceivingAlarmMainEntityList = Lists.newArrayList();
        //获取消息内具体数据
        JSONArray values = data.getJSONArray("values");
        for (Object value : values) {
            ZdReceivingAlarmMainEntity zdReceivingAlarmMainEntity = JSON.parseObject(JSON.toJSONString(value), ZdReceivingAlarmMainEntity.class);
            zdReceivingAlarmMainEntityList.add(zdReceivingAlarmMainEntity);
        }
        return WebSocketCallbackResult.success("息处理成功",msgCode,zdReceivingAlarmMainEntityList);
    }


    @Override
    public void recordsReceivingAlarmLog(String code, BusinessType businessType, String ip,
                                         LogStatus executeStatus,String response,String errorMsg) {
        //如果日志表一致，直接重写父类的方法，如果有变动，这里不能调用父类，可以Overload 重载类方法
        super.writeReceivingAlarmLog(code,businessType,ip,executeStatus,response,errorMsg);
    }

    @Override
    public void saveDate(List<?> records) {
        //切换数据源,运营平台B库
        DataSourceContextUtils.changeZdBusinessDatasourceId();
        this.zdReceivingAlarmMainService.saveBatch((Collection<ZdReceivingAlarmMainEntity>) records);
        DataSourceContextUtils.clearDataSourceKey();
    }

    @Override
    public void onError(Exception e) {
       String errorMsg = StringUtil.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
       log.error("websocket消息回调异常:{}",errorMsg);
    }

}
