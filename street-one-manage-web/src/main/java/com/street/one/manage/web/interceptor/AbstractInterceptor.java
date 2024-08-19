package com.street.one.manage.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.annotation.RepeatSubmit;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.utils.BaseResponseUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.interceptor
 * @ClassName: RepeatSubmitInterceptor
 * @Author: tjl
 * @Description: 防止重复提交拦截器
 * @Date: 2024/6/14 11:40
 * @modified modify person name
 * @Version: 1.0
 */
public abstract class AbstractInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null)
            {
                if (this.isRepeatSubmit(request, annotation))
                {
                    response.getOutputStream().write(JSON.toJSONString(BaseResponseUtil.fail(BaseResponseCodeEnum.E9999,
                            annotation.message())).getBytes());
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @return
     * @throws Exception
     */
    protected boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) throws IOException{
        return false;
    }
}
