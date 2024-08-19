package com.street.one.manage.framework.datasource.dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.street.one.manage.basic.entity.BasisSysDatasourceEntity;
import com.street.one.manage.basic.service.IBasisSysDatasourceService;
import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;
import com.street.one.manage.common.utils.Assert;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.framework.datasource.DataSourceFactory;
import com.street.one.manage.framework.datasource.DataSourceResourceManager;
import com.street.one.manage.framework.datasource.DefaultDataSourceConfig;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.basic.datasource
 * @ClassName: DynamicDataSourceInit
 * @Author: tjl
 * @Description: 动态数据源初始化
 * @Date: 2024/5/31 13:16
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DynamicDataSourceInitialize {

    private final IBasisSysDatasourceService basisSysDatasourceService;

    private final DefaultDataSourceConfig defaultDataSourceConfig;

    /***
     * 初始化数据源
     */
    @Bean
    public void initDataSource() {
        log.info("======初始化动态数据源=====");
        DynamicDataSource dynamicDataSource = SpringApplicationContext.getBean(DynamicDataSource.class);
        Assert.notNull(dynamicDataSource,"动态数据源对象获取失败");
        Map<Object, Object> dsMap = Maps.newHashMap();
        List<BasisSysDatasourceEntity> datasourceList = basisSysDatasourceService.getBaseMapper().selectList(new QueryWrapper<>());
        for (BasisSysDatasourceEntity basisSysDatasourceEntity : datasourceList) {
            BaseDefaultDataSourceInfo baseDefaultDataSourceInfo = new BaseDefaultDataSourceInfo();
            baseDefaultDataSourceInfo.setId(basisSysDatasourceEntity.getDscId());
            baseDefaultDataSourceInfo.setType(basisSysDatasourceEntity.getDbType());
            baseDefaultDataSourceInfo.setUrl(basisSysDatasourceEntity.getDbHost());
            baseDefaultDataSourceInfo.setUsername(basisSysDatasourceEntity.getDbUser());
            baseDefaultDataSourceInfo.setPassword(basisSysDatasourceEntity.getDbPassword());
            baseDefaultDataSourceInfo.setDbname(basisSysDatasourceEntity.getDbName());
            baseDefaultDataSourceInfo.setInitsize(defaultDataSourceConfig.getInitsize());
            baseDefaultDataSourceInfo.setMaxactive(defaultDataSourceConfig.getMaxactive());
            DataSource datasource = DataSourceFactory.createDataSource(baseDefaultDataSourceInfo);
            dsMap.put(baseDefaultDataSourceInfo.getId(), datasource);
            if (null != datasource && basisSysDatasourceEntity.getIsSystemDb()) {
                //设置默认数据源
                dynamicDataSource.setDefaultDataSource(datasource);
                //数据源管理类中设置默认数据源，运系统运行时需要知道默认是那个数据源
                DataSourceResourceManager.setDefaultDataSource(baseDefaultDataSourceInfo);
            }
            //覆盖id相同的数据源
            DataSourceResourceManager.updateDataSource(baseDefaultDataSourceInfo.getId(), baseDefaultDataSourceInfo);
        }
        dynamicDataSource.setDataSources(dsMap);
        //必须调用此方法，不然没法动态切换
        dynamicDataSource.afterPropertiesSet();

    }


}
