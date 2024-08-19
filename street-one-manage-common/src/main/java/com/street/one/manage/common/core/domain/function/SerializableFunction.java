package com.street.one.manage.common.core.domain.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @ProjectName: xhxf-web-portal
 * @Package: com.xhxf.web.portal.manage.common.function
 * @ClassName: SerializableFunction
 * @Author: tjl
 * @Description: 使Function获取序列化能力
 * @Date: 2024/4/18 14:23
 * @modified modify person name
 * @Version: 1.0
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
