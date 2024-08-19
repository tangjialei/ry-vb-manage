package com.street.one.manage.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: Assert
 * @Author: tjl
 * @Description: 断言类，对参数进行验证
 * @Date: 2024/5/14 16:33
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public final class Assert {
     private Assert(){}

    /***
     * 对象不能为空
     * @param object
     */
    public static void notNull(Object object) {
        isNotNull(object, BaseResponseCodeEnum.E10001.getCode(), BaseResponseCodeEnum.E10001.getMsg(),
                Assert.class.getName());
    }

    /***
     * 对象不能为空
     * @param object
     * @param exceptionMessage
     */
    public static void notNull(Object object, String exceptionMessage) {
        isNotNull(object, BaseResponseCodeEnum.E10001.getCode(), exceptionMessage, Assert.class.getName());
    }

    /***
     * 对象不能为空
     * @param object
     * @param responseCodeEnum
     */
    public static void notNull(Object object, BaseResponseCodeEnum responseCodeEnum) {
        isNotNull(object, responseCodeEnum.getCode(), responseCodeEnum.getMsg(), Assert.class.getName());
    }

    /***
     * 对象不能为空
     * @param object
     * @param exceptionCode
     * @param exceptionMessage
     */
    public static void notNull(Object object, String exceptionCode, String exceptionMessage) {
        isNotNull(object, exceptionCode, exceptionMessage, Assert.class.getName());
    }

    /***
     * 集合不能空
     * @param object
     * @param msg
     */
    public static void notCollectNull(Collection<?> object, String msg) {
        if(CollectionUtil.isEmpty(object)){
            isNotNull(null,BaseResponseCodeEnum.E10001.getCode(), msg, Assert.class.getName());
        }
    }

    /***
     * 集合不能空
     * @param object
     */
    public static void notCollectNull(Collection<?> object) {
        if(CollectionUtil.isEmpty(object)){
            isNotNull(null, BaseResponseCodeEnum.E10001.getCode(), BaseResponseCodeEnum.E10001.getMsg(), Assert.class.getName());
        }
    }

    /***
     * 数组不能为空
     * @param objects
     * @param msg
     */
    public static void notArrayNull(Object[] objects, String msg) {
        if(objects == null || objects.length == 0) {
            isNotNull(null, BaseResponseCodeEnum.E10001.getCode(), msg, Assert.class.getName());
        }
    }

    /***
     * 数组不能为空
     * @param objects
     */
    public static void notArrayNull(Object[] objects) {
        if(objects == null || objects.length == 0) {
            isNotNull(null, BaseResponseCodeEnum.E10001.getCode(), BaseResponseCodeEnum.E10001.getMsg(), Assert.class.getName());
        }
    }

    /***
     * map集合不能为空
     * @param map
     * @param msg
     */
    public static void notMapNull(Map<Integer, ?> map, String msg) {
        if(map == null || map.isEmpty()) {
            isNotNull(null, BaseResponseCodeEnum.E10001.getCode(), msg, Assert.class.getName());
        }
    }

    /***
     * map集合不能为空
     * @param map
     */
    public static void notMapNull(Map<String, ?> map) {
        if(map == null || map.isEmpty()) {
            isNotNull(null, BaseResponseCodeEnum.E10001.getCode(), BaseResponseCodeEnum.E10001.getMsg(), Assert.class.getName());
        }
    }

    public static void notEmptyOrNull(String str) {
        isEmptyOrNull(str, BaseResponseCodeEnum.E10001.getCode(), BaseResponseCodeEnum.E10001.getMsg(), Assert.class.getName());
    }

    public static void notEmptyOrNull(String str, BaseResponseCodeEnum responseCodeEnum) {
        isEmptyOrNull(str, responseCodeEnum.getCode(), responseCodeEnum.getMsg(), Assert.class.getName());
    }

    public static void notEmptyOrNull(String str, String message) {
        isEmptyOrNull(str, BaseResponseCodeEnum.E10001.getCode(), message, Assert.class.getName());
    }


    /**
     * 参数不能为null，如果是null会抛异常并记录日志
     * @param object           需要校验的参数
     * @param exceptionCode    异常code
     * @param exceptionMessage 异常信息
     * @param className        记录日志的类名
     */
    public static void isNotNull(Object object, String exceptionCode, String exceptionMessage, String className) {
        if (object == null) {
            log.error("{}类参数错误，参数：{} 不能为null：{}", className, object, exceptionMessage);
            throw new BusinessException(exceptionCode, exceptionMessage);
        }
    }


    /**
     * 参数不能为null/空字符串，否则会报错并记录日志
     * @param exceptionCode    异常code
     * @param exceptionMessage 异常信息
     * @param className        记录日志的类名
     */
    public static void isEmptyOrNull(String str, String exceptionCode, String exceptionMessage, String className) {
        if (StrUtil.isBlank(str)) {
            log.error("{}类参数错误，参数：{}不能为空：{}", className, str, exceptionMessage);
            throw new BusinessException(exceptionCode, exceptionMessage);
        }
    }

}
