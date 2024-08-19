package com.street.one.manage.framework.datasource.dynamic;

import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.framework.datasource.DataSourceResourceManager;
import com.street.one.manage.framework.datasource.DetermineCurrentDataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DynamicDataSource
 * @Author: tjl
 * @Description: 动态数据源实现 AbstractRoutingDataSource 接口
 * @Date: 2024/5/30 10:38
 * @modified modify person name
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        //没有设置数据源的时候，设备系统默认的数据源
        if (StringUtil.isEmptyOrNull(DetermineCurrentDataSourceHolder.getDataSourceId())) {
            DetermineCurrentDataSourceHolder.setDataSourceId(DataSourceResourceManager.systemDataBaseInfo.getId());
        }
        return DetermineCurrentDataSourceHolder.getDataSourceId();

    }



    /**
     * 设置默认数据源
     * @param defaultDataSource
     */
    public void setDefaultDataSource(Object defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    public void setDataSources(Map<Object, Object> dataSources) {
        super.setTargetDataSources(dataSources);
    }

}
