package com.street.one.manage.framework.datasource;

import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;
import com.street.one.manage.common.core.domain.model.DataSourceContext;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.framework.datasource.dynamic.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DataSourceFactory
 * @Author: tjl
 * @Description: 数据源工厂类
 * @Date: 2024/5/30 11:42
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public class DataSourceFactory {

    /***
     * 系统数据源
     */
    private static DataSource systemDB = null;

    /***
     * 系统数据源id
     */
    private static String systemDbId = null;


    /***
     * 根据默认配置创建数据源
     * @param info
     * @return
     */
    public static DataSource createDataSource(BaseDefaultDataSourceInfo info) {
        try {
            return DruidDataSourceBuild.createDataSource(info);
        } catch (Exception e) {
            return null;
        }

    }

    /***
     * 获取数据源
     * @param dsc
     * @return
     */
    public static DataSource getDataSource(DataSourceContext dsc) {
        if (systemDB == null){
            if (DataSourceResourceManager.systemDataBaseInfo.getId().equals(dsc.getDataSourceId())) {
                systemDB = createDataSource(DataSourceResourceManager.systemDataBaseInfo);
                systemDbId = dsc.getDataSourceId();
                return systemDB;
            }
        }
        DynamicDataSource datasource = SpringApplicationContext.getBean(DynamicDataSource.class);
        if(CommonConstants.SYS_TE_MDB.equals(dsc.getMode())){
            // system db
            DetermineCurrentDataSourceHolder.setDataSourceId(systemDbId);
        }else{
            // 切换数据源
            DetermineCurrentDataSourceHolder.setDataSourceId(dsc.getDataSourceId());
        }
        return datasource;
    }
}
