package com.street.one.manage.common.annotation;

import java.lang.annotation.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.annotation
 * @ClassName: RepeatSubmit
 * @Author: tjl
 * @Description: 自定义注解防止表单重复提交
 * @Date: 2024/6/14 11:38
 * @modified modify person name
 * @Version: 1.0
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /***
     * 间隔时间(ms)，小于此时间视为重复提交
     * @return
     */
    int interval() default 5000;

    /***
     * 提示消息
     * @return
     */
    String message() default "不允许重复提交，请稍候再试";

}
