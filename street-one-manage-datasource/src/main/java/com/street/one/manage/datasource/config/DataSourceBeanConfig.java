package com.street.one.manage.datasource.config;

import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;
import com.street.one.manage.framework.datasource.dynamic.DynamicDataSource;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DataSourceConfig
 * @Author: tjl
 * @Description: 多数据源配置类
 * @Date: 2024/4/25 17:34
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
public class DataSourceBeanConfig {

    @Autowired
    public  DefaultDataSourceConfig defaultDataSourceConfig;

    @Bean("defaultDataSource")
    public DataSource defaultDataSource() {
        BaseDefaultDataSourceInfo  defaultDataSourceInfo = new BaseDefaultDataSourceInfo();
        BeanUtils.copyProperties(defaultDataSourceConfig,defaultDataSourceInfo);
       return DataSourceFactory.createDataSource(defaultDataSourceInfo);
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(defaultDataSourceConfig.getId(), defaultDataSource());
        // 将 master 数据源作为默认指定的数据源
        dynamicDataSource.setDefaultDataSource(defaultDataSource());
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        //默认数据源id
        DetermineCurrentDataSourceHolder.setDataSourceId(defaultDataSourceConfig.getId());
        return dynamicDataSource;
    }

}