package com.street.one.manage.common.enums;

/**
 * @author tjl
 */

public enum BaseResponseCodeEnum {
    /**
     * 参数错误
     */
    E400("400", "参数错误"),
    /**
     * token 失效
     */
    E401("401", "token失效，请重新获取!"),
    /**
     * Forbidden
     */
    E403("403", "Forbidden"),
    /**
     * 资源不存在
     */
    E404("404", "资源不存在"),

    /**
     * 方法错误
     */
    E9993("9993", "请求方式方法不支持"),
    /**
     * 系统代码错误
     */
    E9994("9994", "缓存异常"),
    /***
     * 网络异常
     */
    E9995("9995", "网络异常"),

    /**
     * 系统代码错误
     */
    E9996("9996", "代码错误"),

    /**
     * 系统异常
     */
    E9999("9999", "系统异常"),
    /**
     * 配置错误
     */
    E9998("9998", "配置错误"),
    /**
     * 参数为空错误码
     */
    E10001("10001", "参数为空"),

    /* ----------------------------------------------------- */
    // 所有系统共用返回码
    /* ----------------------------------------------------- */
    /**
     * 接口处理成功
     */
    SUCCESS("0000", "处理成功"),

    E0001("0001", "处理中"),
    /**
     * 无特殊要求时，统一使用此接口失败返回码
     */
    E9000("9000", "处理失败"),

    /** 业务返回码 */
    /**
     * 用户不存在
     */
    E1000("1000", "用户不存在"),
    E1001("1001", "账号不能使用"),
    E1002("1002", "用户手机已绑定"),
    E1003("1003", "用户名或密码错误"),
    E1004("1004", "新用户"),
    E1005("1005", "对不起,您没有权限,请联系管理员"),
    E1006("1006", "请输入校验码"),
    E1007("1007", "校验码错误"),
    E1008("1008", "密码必须包含大写、小写、数字和特殊字符，且长度是8位以上");


    /**
     * 公共
     */
    public static final String PREFIX = "E";

    private String code;

    private String msg;

    BaseResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * return BaseResponseCode.valueOf(prefix + code);
     *
     * @param code
     * @param prefix
     * @return
     */
    public static BaseResponseCodeEnum valueOf(String prefix, String code) {
        return BaseResponseCodeEnum.valueOf(prefix + code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
