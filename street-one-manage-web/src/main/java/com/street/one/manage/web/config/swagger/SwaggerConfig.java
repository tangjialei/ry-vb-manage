package com.street.one.manage.web.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: SwaggerConfig
 * @Author: tjl
 * @Description: Swagger API文档相关配置
 * @Date: 2023/6/25 11:29
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.street.one.manage.webapi")
                .title("街镇版一网统管")
                .description("街镇版一网统管服务相关接口文档")
                .contactName("jialei.tang")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
