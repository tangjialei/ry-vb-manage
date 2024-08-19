package com.street.one.manage.common.core.domain;

import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tjl
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 4301300363535985841L;

    /***
     * 返回代码
     */
    private String code;
    /***
     * 返回消息
     */
    private String message;
    /***
     * 返回体
     */
    private T data;

    public BaseResponse(BaseResponseCodeEnum code, String message, T data) {
        this.code = code.getCode();
        if (message == null) {
            this.message = code.getMsg();
        } else {
            this.message = message;
        }
        this.data = data;
    }

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
