package com.street.one.manage.common.utils;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-wx
 * @Package: com.xhxf.manage.common.utils
 * @ClassName: DateUtil
 * @Author: tjl
 * @Description: 业务内返回对象
 * @Date: 2023/6/25 10:37
 * @modified modify person name
 * @Version: 1.0
 */
public class BizBaseResult implements Serializable {

    private static final long serialVersionUID = 4860870353862702598L;

    private Boolean success = true;

    private String msg;

    private Object data;

    public BizBaseResult() {
    }

    public BizBaseResult(Boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public BizBaseResult(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BizBaseResult fail(String msg) {
        return new BizBaseResult(false, msg,null);
    }
    public static BizBaseResult fail(String msg,Object data) {
        return new BizBaseResult(false, msg,data);
    }

    public static BizBaseResult success(String msg) {
        return new BizBaseResult(true, msg,null);
    }
    public static BizBaseResult success(String msg,Object data) {
        return new BizBaseResult(true, msg,data);
    }

}
