package com.street.one.manage.common.utils;


import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;

/**
 * @author tjl
 */
public class BaseResponseUtil {

    public static BaseResponse success() {
        return new BaseResponse(BaseResponseCodeEnum.SUCCESS, null, null);
    }

    public static BaseResponse success(Object object) {
        return new BaseResponse(BaseResponseCodeEnum.SUCCESS, null, object);
    }

    public static BaseResponse success(Object object, String message) {
        return new BaseResponse(BaseResponseCodeEnum.SUCCESS, message, object);
    }

    public static BaseResponse fail(BaseResponseCodeEnum code, Object object) {
        return new BaseResponse(code, null, object);
    }

    public static BaseResponse fail(String code,String message) {
        return new BaseResponse(code, message, null);
    }

    public static BaseResponse fail(BaseResponseCodeEnum code) {
        return new BaseResponse(code, code.getMsg(), null);
    }

    public static BaseResponse fail(BaseResponseCodeEnum code, String message) {
        return new BaseResponse(code, message, null);
    }

    public static BaseResponse fail(String message) {
        return new BaseResponse(BaseResponseCodeEnum.E9000, message, null);
    }

    public static boolean checkResult(BaseResponse response) {
        if (response == null) {
            return false;
        }
        if (!BaseResponseCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
            return false;
        }
        return true;
    }

    public static boolean checkResultAndBody(BaseResponse response) {
        if (response == null) {
            return false;
        }
        if (!BaseResponseCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
            return false;
        }
        if (response.getData() == null) {
            return false;
        }
        return true;
    }
}
