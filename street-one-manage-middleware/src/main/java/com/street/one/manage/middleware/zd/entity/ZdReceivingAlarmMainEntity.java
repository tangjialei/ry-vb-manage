package com.street.one.manage.middleware.zd.entity;

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
 * 接处警_案件信息(迪艾斯推送的数据记录)
 * </p>
 *
 * @author tjl
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zd_receiving_alarm_main")
public class ZdReceivingAlarmMainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键主增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 逻辑代码,全局唯一
     */
    @TableField("code")
    private String code;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 案件等级
     */
    @TableField("case_grade")
    private String caseGrade;

    /**
     * 报警时间
     */
    @TableField("alarm_time")
    private LocalDateTime alarmTime;

    /**
     * 案发地址
     */
    @TableField("incident_addr")
    private String incidentAddr;

    /**
     * 案件类型
     */
    @TableField("case_type")
    private String caseType;

    /**
     * 处置对象
     */
    @TableField("handle_object")
    private String handleObject;

    /**
     * 报警电话
     */
    @TableField("alarm_telephone")
    private String alarmTelephone;

    /**
     * 报警人
     */
    @TableField("alarm_person")
    private String alarmPerson;

    /**
     * x坐标
     */
    @TableField("longitude")
    private String longitude;

    /**
     * y坐标
     */
    @TableField("latitude")
    private String latitude;


    /**
     * 城运纬度
     */
    @TableField("city_y")
    private String cityY;

    /**
     * 城运经度
     */
    @TableField("city_x")
    private String cityX;

    /**
     * 所属居委
     */
    @TableField("institution_jw")
    private String institutionJw;

    /**
     * 所属街道
     */
    @TableField("institution_street")
    private String institutionStreet;

    /**
     * 所属网格
     */
    @TableField("institution_grid")
    private String institutionGrid;

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
