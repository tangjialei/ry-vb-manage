package com.street.one.manage.web.config.log;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: LogConfigurer
 * @Author: tjl
 * @Description: 请求返回日志配置
 * @Date: 2024/5/13 16:40
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
public class LogConfigurer implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> logFilterBean = new FilterRegistrationBean<>();
        logFilterBean.setFilter(new LogFilter());
        logFilterBean.addUrlPatterns("/*");
        logFilterBean.setOrder(1);
        return logFilterBean;
    }
}
