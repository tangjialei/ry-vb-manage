package com.street.one.manage.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author tjl
 * @since 2024-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_dict_code")
public class BasisDictCodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典代码
     */
    @TableField("code")
    private String code;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 字典代码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 字典类型名称
     */
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 是否废除:1-正常;0-删除
     */
    @TableField("is_del")
    private Boolean isDel;

    /**
     * 是否显示:0-隐藏;1-显示
     */
    @TableField("is_show")
    private Boolean isShow;

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
