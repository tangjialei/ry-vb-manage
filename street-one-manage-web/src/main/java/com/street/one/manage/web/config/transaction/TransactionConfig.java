//package com.center.crm.web.config.transaction;
//
//import com.center.crm.basic.datasource.dynamic.DynamicDataSource;
//import com.center.crm.common.utils.SpringApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * @ProjectName: data-center-crm
// * @Package: com.center.crm.web.config
// * @ClassName: TransactionConfig
// * @Author: tjl
// * @Description: 事务配置项
// * @Date: 2024/4/29 14:19
// * @modified modify person name
// * @Version: 1.0
// */
//@Configuration
//public class TransactionConfig {
//
//
//    /**
//     * 大后台事务管理器
//     */
//    @Bean("crmTransactionManager")
//    @Primary
//    public PlatformTransactionManager getTransactionManagerForZt(){
//        DynamicDataSource dynamicDataSource = SpringApplicationContext.getBean(DynamicDataSource.class);
//        return new DataSourceTransactionManager(dynamicDataSource);
//    }
//
//}
