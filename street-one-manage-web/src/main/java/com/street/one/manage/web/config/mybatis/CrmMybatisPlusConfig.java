//package com.center.crm.web.config.mybatis;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @ProjectName: data-center-crm
// * @Package: com.center.crm.web.config.mybatis
// * @ClassName: CrmMybatisPlusConfig
// * @Author: tjl
// * @Description: 分页插件配置
// * @Date: 2024/5/28 13:28
// * @modified modify person name
// * @Version: 1.0
// */
//@Configuration
//public class CrmMybatisPlusConfig {
//
//    @Bean("crmMybatisPlusInterceptor")
//    @ConditionalOnClass(PaginationInnerInterceptor.class)
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//
//        //分页优化
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
//        paginationInnerInterceptor.setOptimizeJoin(false);
//        interceptor.addInnerInterceptor(paginationInnerInterceptor);
//
//        //乐观锁
//        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
//        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
//
//        return interceptor;
//    }
//
//}
