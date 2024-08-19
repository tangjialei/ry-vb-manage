package com.street.one.manage;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web
 * @ClassName: CrmWebApplication
 * @Author: tjl
 * @Description: 街镇版一网统管后台程序启动主类
 * @Date: 2024/5/13 15:16
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableAsync
@ServletComponentScan
@MapperScan(value = {"com.street.one.manage.*.mapper","com.street.one.manage.*.*.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
public class StreetOneManageApplication {

    public static void main(String[] args) {
        //设置默认时区：东八区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        //设置默认地区：中国
        Locale.setDefault(Locale.CHINA);
        SpringApplication.run(StreetOneManageApplication.class, args);
        log.info("=====================程序启动成功！=========================");
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        // 2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
        //SerializerFeature.WriteNullBooleanAsFalse
        // 3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(new MediaType("text", "json", StandardCharsets.UTF_8));
        fastMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        // 4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }

}
