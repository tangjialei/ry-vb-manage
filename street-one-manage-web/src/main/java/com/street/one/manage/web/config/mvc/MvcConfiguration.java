package com.street.one.manage.web.config.mvc;

import com.street.one.manage.web.interceptor.AuthTokenInterceptor;
import com.street.one.manage.web.interceptor.SameUrlDataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: MvcConfiguration
 * @Author: tjl
 * @Description: 拦截器请求配置
 * @Date: 2024/5/13 16:40
 * @modified modify person name
 * @Version: 1.0
 */
@EnableAsync
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private SameUrlDataInterceptor sameUrlDataInterceptor;

    @Autowired
    private AuthTokenInterceptor authTokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义排除swagger访问的路径、登录接口
        String[] swaggerExcludes = new String[]{"/swagger-ui.html", "/swagger-resources/**",
                "/webjars/**","/error","/access/**","/client/**","/alarm-info/**","/websocket/**","/v1/basic/verify/**","/v1/report/excel/download_excel_template","/v1/third/**"};
        registry.addInterceptor(authTokenInterceptor).addPathPatterns("/**")
            .excludePathPatterns(swaggerExcludes);
        registry.addInterceptor(sameUrlDataInterceptor).addPathPatterns("/**");
    }


}
