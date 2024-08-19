package com.street.one.manage.basic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方接口规约配置表
 * </p>
 *
 * @author tjl
 * @since 2024-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_third_config")
public class BasisThirdConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 第三方配置代码
     */
    @TableField("third_config_code")
    private String thirdConfigCode;

    /**
     * 第三方项目名称简写
     */
    @TableField("third_name")
    private String thirdName;

    /**
     * 第三方IP前缀地址
     */
    @TableField("third_ip_prefix")
    private String thirdIpPrefix;

    /**
     * JSON存放,请求固定参数
     */
    @TableField("third_req_params")
    private String thirdReqParams;

    /**
     * ST002:启用，ST001：停用
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
