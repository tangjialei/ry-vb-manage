package com.street.one.manage.framework.datasource;

import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DataSourceResource
 * @Author: tjl
 * @Description: 系统数据源管理类
 * @Date: 2024/5/30 10:53
 * @modified modify person name
 * @Version: 1.0
 */
public class DataSourceResourceManager {

    /***
     * 默认数据源集合
     */
    private static Map<String, BaseDefaultDataSourceInfo> datasource = Maps.newHashMap();

    /***
     * 系统默认使用的数据源
     */
    public static BaseDefaultDataSourceInfo systemDataBaseInfo;

    /***
     * 设置默认数据源
     * @param value
     */
    public static void setDefaultDataSource(BaseDefaultDataSourceInfo value) {
        systemDataBaseInfo = value;
    }


    /***
     * 更新默认数据源
     * @param key
     * @param value
     */
    public static void updateDataSource(String key, BaseDefaultDataSourceInfo value) {
        datasource.put(key, value);
    }

    /***
     * 获取默认数据源
     * @param key
     * @return
     */
    public static BaseDefaultDataSourceInfo get(String key) {
        return datasource.get(key);
    }

}

