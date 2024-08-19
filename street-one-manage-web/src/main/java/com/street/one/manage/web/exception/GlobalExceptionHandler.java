package com.street.one.manage.web.exception;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.exception.BusinessException;
import com.street.one.manage.common.utils.BaseResponseUtil;
import com.street.one.manage.common.utils.StringUtil;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.exception
 * @ClassName: GlobalExceptionHandler
 * @Author: tjl
 * @Description: 全局异常控制类
 * @Date: 2023/6/21 10:04
 * @modified modify person name
 * @Version: 1.0
 */
@RestControllerAdvice
@Slf4j
@Configuration
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /***
     * 业务自定义异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessException(BusinessException e, HttpServletRequest request) {
        log.error("当前请求路径'{}',业务自定义异常.", request.getRequestURI(), e);
        return StringUtil.isEmptyOrNull(e.getCode()) ? BaseResponseUtil.fail(e.getMessage()) : BaseResponseUtil.fail(e.getCode(),e.getMessage());
    }

    /***
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exception(Exception e, HttpServletRequest request) {
        log.error("当前请求路径'{}',发生系统异常.", request.getRequestURI(), e);
        return BaseResponseUtil.fail(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        log.error("当前请求路径'{}',发生未知异常.", request.getRequestURI(), e);
        return BaseResponseUtil.fail(e.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<?> requestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        log.error("当前请求路径：{}, 参数: {}", request.getRequestURI(), getRequestJsonString(request));
        log.error(e.getMessage(),e);
        return BaseResponseUtil.fail(BaseResponseCodeEnum.E9993);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> messageExceptionHandler(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("当前请求路径：{}, 参数: {}", request.getRequestURI(), getRequestJsonString(request));
        log.error(e.getMessage(), e);
        return BaseResponseUtil.fail(BaseResponseCodeEnum.E400);
    }

    /***
     * 获取请求参数
     * @param request
     * @return
     */
    public static String getRequestJsonString(HttpServletRequest request) {
        if (CommonConstants.METHOD_TYPE.equalsIgnoreCase(request.getMethod())) {
            if(StringUtils.isNotBlank(request.getQueryString())) {
                return new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)
                        .replaceAll("%22", "\"");
            }else {
                return "";
            }
        }else {
            Enumeration<String> parameterNames = request.getParameterNames();
            Map<String, Object> map = Maps.newHashMap();
            if (null != parameterNames) {
                while (parameterNames.hasMoreElements()) {
                    String key = parameterNames.nextElement();
                    String value = request.getParameter(key);
                    map.put(key, value);
                }
            }
            return JSON.toJSONString(map);
        }
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse<?> exceptionHandler(BindException e){
        e.printStackTrace();
        return BaseResponseUtil.fail(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }


}
