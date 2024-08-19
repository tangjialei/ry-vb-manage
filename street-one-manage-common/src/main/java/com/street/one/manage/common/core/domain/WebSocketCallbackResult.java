package com.street.one.manage.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain
 * @ClassName: WebSocketCallbackResult
 * @Author: tjl
 * @Description: WebSocketCallback 回调返回的处理数据
 * @Date: 2024/7/4 16:36
 * @modified modify person name
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketCallbackResult implements Serializable {
    private static final long serialVersionUID = 7628168084803294759L;

    /***
     * 是否成功
     */
    private Boolean success = true;

    /***
     * 消息
     */
    private String msg;

    /**
     * 日志代码,全局唯一
     */
    private String code;

    /***
     * 具体数据
     */
    private List<?> values;


    public static WebSocketCallbackResult fail(String msg) {
        return new WebSocketCallbackResult(false, msg,null,null);
    }

    public static WebSocketCallbackResult fail(String msg,String code) {
        return new WebSocketCallbackResult(false, msg,code,null);
    }

    public static WebSocketCallbackResult success(String msg) {
        return new WebSocketCallbackResult(true, msg,null,null);
    }
    public static WebSocketCallbackResult success(String msg,String code,List<?> data) {
        return new WebSocketCallbackResult(true, msg,code, data);
    }


}
