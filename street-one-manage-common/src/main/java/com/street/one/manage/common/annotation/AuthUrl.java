package com.street.one.manage.common.annotation;

import com.street.one.manage.common.enums.SecretTypeEnum;

import java.lang.annotation.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.annotation
 * @ClassName: AuthUrl
 * @Author: tjl
 * @Description: 判断不同请求url权限
 * @Date: 2024/5/13 下午5:00
 * @modified modify person name
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUrl {

    /***
     * 默认平台接口
     * @return SecretTypeEnum
     */
    SecretTypeEnum[] thirdType() default {SecretTypeEnum.THIRD};
}
