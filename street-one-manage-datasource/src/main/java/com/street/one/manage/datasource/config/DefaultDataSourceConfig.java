package com.street.one.manage.datasource.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.base
 * @ClassName: BaseDefaultDataSource
 * @Author: tjl
 * @Description: 默认的数据源
 * @Date: 2024/5/30 10:48
 * @modified modify person name
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.datasource.defaultdb")
public class DefaultDataSourceConfig {

    private String id;
    private String type;
    private String url;
    private String username;
    private String password;
    private String dbname;
    private String initsize;
    private String maxactive;


}
