package com.street.one.manage.common.exception;

import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import lombok.Getter;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.exception
 * @ClassName: BusinessException
 * @Author: tjl
 * @Description: 业务异常
 * @Date: 2023/6/21 10:05
 * @modified modify person name
 * @Version: 1.0
 */
@Getter
public class BusinessException extends  RuntimeException {
    private static final long serialVersionUID = 6417626824656701541L;


    /***
     * 系统异常
     */
    private String code;

    /**
     * 错误提示
     */
    private String message;

    public BusinessException() {

    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public BusinessException(BaseResponseCodeEnum responseCodeEnum) {
        this.message = responseCodeEnum.getMsg();
        this.code = responseCodeEnum.getCode();
    }

}
