package com.street.one.manage.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.config
 * @ClassName: QuartzConfig
 * @Author: tjl
 * @Description: Quartz配置类
 * @Date: 2024/7/3 16:02
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
public class QuartzConfig {

    /**
     * 配置SchedulerFactoryBean
     *
     * 将一个方法产生为Bean并交给Spring容器管理
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        // Spring提供SchedulerFactoryBean为Scheduler提供配置信息,并被Spring容器管理其生命周期
        SchedulerFactoryBean factoryBean= new SchedulerFactoryBean();
        //scheduler 名称
        factoryBean.setSchedulerName("xhxf-street-one-manage-scheduler");
        // 设置自动启动，默认为true
        factoryBean.setAutoStartup(true);
        return factoryBean;
    }

}
