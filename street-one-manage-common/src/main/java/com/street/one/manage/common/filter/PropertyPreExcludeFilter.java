package com.street.one.manage.common.filter;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.filter
 * @ClassName: PropertyPreExcludeFilter
 * @Author: tjl
 * @Description: 排除JSON敏感属性
 * @Date: 2024/6/21 18:12
 * @modified modify person name
 * @Version: 1.0
 */
public class PropertyPreExcludeFilter extends SimplePropertyPreFilter {

    public PropertyPreExcludeFilter addExcludes(String... filters) {
        for (String filter : filters) {
            this.getExcludes().add(filter);
        }
        return this;
    }

}
