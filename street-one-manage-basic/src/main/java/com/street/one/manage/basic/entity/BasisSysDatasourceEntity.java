package com.street.one.manage.basic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统数据源配置表
 * </p>
 *
 * @author tjl
 * @since 2024-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_sys_datasource")
public class BasisSysDatasourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据源ID
     */
    @TableId("dsc_id")
    private String dscId;

    /**
     * 名称
     */
    @TableField("dsc_name")
    private String dscName;

    /**
     * 数据源类型
     */
    @TableField("db_type")
    private String dbType;

    /**
     * 数据源地址
     */
    @TableField("db_host")
    private String dbHost;

    /**
     * 数据源名称
     */
    @TableField("db_name")
    private String dbName;

    /**
     * 数据源连接账号
     */
    @TableField("db_user")
    private String dbUser;

    /**
     * 数据源连接密码
     */
    @TableField("db_password")
    private String dbPassword;

    /**
     * 是否系统默认:0-不是默认;1-默认
     */
    @TableField("is_system_db")
    private Boolean isSystemDb;

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
