//package com.center.crm.web.config.mybatis;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.center.crm.basic.datasource.dynamic.DynamicDataSource;
//import com.center.crm.common.utils.SpringApplicationContext;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @ProjectName: data-center-crm
// * @Package: com.center.crm.web.config
// * @ClassName: CrmMapperConfig
// * @Author: tjl
// * @Description: data-center-crm  Mapper 管理
// * @Date: 2024/5/16 12:18
// * @modified modify person name
// * @Version: 1.0
// */
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = {"com.center.crm.*.mapper"},
//        sqlSessionTemplateRef = "crmSessionMapper")
//@Slf4j
//public class CrmMapperConfig {
//
//    @Bean
//    @Primary
//    public SqlSessionFactory crmMapperSqlSessionFactory(MybatisPlusProperties mybatisPlusProperties,
//                                                        @Qualifier("crmMybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor
//                                                       ) throws Exception {
//        //获取动态数据源
//        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringApplicationContext.getBean("dynamicDataSource");
//        //MyBatis全局配置
//        GlobalConfig globalConfig = new GlobalConfig();
//        //关闭banner
//        globalConfig.setBanner(false);
//        //sql工厂
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        //设置数据源
//        bean.setDataSource(dynamicDataSource);
//        //设置全局配置
//        bean.setGlobalConfig(globalConfig);
//        //获取 mybatis-plus yml配置
//        bean.setConfiguration(mybatisPlusProperties.getConfiguration());
//        // 获取 MybatisPlusProperties 中配置的 mapperLocations
//        String[] mapperLocations = mybatisPlusProperties.getMapperLocations();
//        // 使用 PathMatchingResourcePatternResolver 解析 mapperLocations
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        List<Resource> resources = new ArrayList<>();
//        for (String location : mapperLocations) {
//            try {
//                Resource[] locationResources = resolver.getResources(location);
//                resources.addAll(Arrays.asList(locationResources));
//            } catch (IOException e) {
//                // 处理异常
//                e.printStackTrace();
//            }
//        }
//        // 将解析后的 Resource 数组设置到 MybatisSqlSessionFactoryBean 中
//        bean.setMapperLocations(resources.toArray(new Resource[0]));
//        //分页插件
//        bean.setPlugins(mybatisPlusInterceptor);
//        return bean.getObject();
//    }
//
//
//    @Bean
//    public SqlSessionTemplate crmSessionMapper(@Qualifier("crmMapperSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        log.info("==================初始化业务数据库成功！==================");
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
