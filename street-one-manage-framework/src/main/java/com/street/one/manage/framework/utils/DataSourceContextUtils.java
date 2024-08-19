package com.street.one.manage.framework.utils;

import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;
import com.street.one.manage.common.core.domain.model.DataSourceContext;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.framework.datasource.DataSourceResourceManager;
import com.street.one.manage.framework.datasource.DetermineCurrentDataSourceHolder;
import com.street.one.manage.framework.datasource.dynamic.DynamicDataSource;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: LoginContextUtils
 * @Author: tjl
 * @Description: 数据源上下文
 * @Date: 2024/5/30 13:16
 * @modified modify person name
 * @Version: 1.0
 */
public class DataSourceContextUtils {

    /**
     * 动态数据源
     */
   private static DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringApplicationContext.getBean("dynamicDataSource");

    /**
     * 切换数据源，一个数据库实例，多个库切换使用
     *
     * @param datasourceId
     */
    public static void checkoutDefaultDataSource(String datasourceId) {
        if (dynamicDataSource != null) {
            // 切换数据源
            DetermineCurrentDataSourceHolder.setDataSourceId(datasourceId);
            dynamicDataSource.afterPropertiesSet();
        }
    }

    /**
     * 切换到默认数据源
     */
    public static void clearDataSourceKey() {
       DetermineCurrentDataSourceHolder.clearDataSourceHolder();
    }

    /**
     * 获取当前数据源
     *
     * @return
     */
    public static String getDataSourceKey() {
        if (dynamicDataSource != null) {
            return DetermineCurrentDataSourceHolder.getDataSourceId();
        } else {
            return null;
        }
    }


    /***
     * 获取默认的数据源，jdbcTemplate 使用
     * @return
     */
    public static DataSourceContext getDefaultDataSource() {
        DataSourceContext dataSource = new DataSourceContext();
        dataSource.setDataSourceId(DataSourceResourceManager.systemDataBaseInfo.getId());
        dataSource.setDbType(DataSourceResourceManager.systemDataBaseInfo.getType());
        dataSource.setMode(CommonConstants.SYS_TE_MDB);
        return dataSource;
    }

    /***
     * 动态获取数据源，jdbcTemplate 使用
     * @param key
     * @return
     */
    public static DataSourceContext getDynamicDataSourceContext(String key) {
        BaseDefaultDataSourceInfo baseDefaultDataSourceInfo = DataSourceResourceManager.get(key);
        DataSourceContext dataSourceResp = new DataSourceContext();
        dataSourceResp.setDataSourceId(baseDefaultDataSourceInfo.getId());
        dataSourceResp.setDbType(baseDefaultDataSourceInfo.getType());
        return dataSourceResp;
    }

    /***
     * 变更总队业务数据库
     * @return
     */
    public static void changeZdBusinessDatasourceId(){
        checkoutDefaultDataSource("164b965961034b52bfda57b9f329882b");
    }
}
