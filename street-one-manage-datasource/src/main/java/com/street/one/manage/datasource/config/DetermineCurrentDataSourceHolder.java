package com.street.one.manage.datasource.config;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DetermineCurrentDataSourceHolder
 * @Author: tjl
 * @Description: 本地线程动态数据源处理类
 * @Date: 2024/5/30 10:37
 * @modified modify person name
 * @Version: 1.0
 */
public class DetermineCurrentDataSourceHolder {

    /***
     * 本地线程存放数据源id，标识当前使用的是那个数据源
     */
    private static final ThreadLocal<String> DATA_SOURCE_HOLDER = new ThreadLocal<>();

    public static void setDataSourceId(String datasourceId) {
        DATA_SOURCE_HOLDER.set(datasourceId);
    }

    public static String getDataSourceId() {
        return DATA_SOURCE_HOLDER.get();
    }

    public static void clearDataSourceHolder() {
        DATA_SOURCE_HOLDER.remove();
    }


}
