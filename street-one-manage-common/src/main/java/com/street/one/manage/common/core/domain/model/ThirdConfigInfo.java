package com.street.one.manage.common.core.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.model
 * @ClassName: ThirdConfigInfo
 * @Author: tjl
 * @Description: 第三方配置详情
 * @Date: 2024/6/14 17:57
 * @modified modify person name
 * @Version: 1.0
 */
@Getter
@Setter
public class ThirdConfigInfo implements Serializable {


    private static final long serialVersionUID = -3811508405908072098L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 第三方配置代码
     */
    private String thirdConfigCode;

    /**
     * 第三方项目名称简写
     */
    private String thirdName;

    /**
     * 第三方IP前缀地址
     */
    private String thirdIpPrefix;

    /**
     * JSON存放,请求固定参数
     */
    private String thirdReqParams;

    /**
     * ST002:启用，ST001：停用
     */
    private String status;



}
