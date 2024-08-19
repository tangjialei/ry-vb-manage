package com.street.one.manage.middleware.zd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;

import com.street.one.manage.common.core.domain.model.AbstractInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 接处警_案件基本信息详情
 * </p>
 *
 * @author tjl
 * @since 2024-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zd_receiving_alarm_main_detail")
public class ZdReceivingAlarmMainDetailEntity extends AbstractInfo {

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
     * 徐汇消防总队数据id,用于回查需求,这里做保存
     */
    @TableField("third_id")
    private String thirdId;

    /**
     * 接警记录ID
     */
    @TableField("receiving_alarm_info_id")
    private String receivingAlarmInfoId;

    /**
     * 接警员姓名
     */
    @TableField("receiving_alarm_member_name")
    private String receivingAlarmMemberName;

    /**
     * 立案接警序号
     */
    @TableField("register_receiving_alarm_seq")
    private String registerReceivingAlarmSeq;

    /**
     * 立案方式代码
     */
    @TableField("register_way_code")
    private String registerWayCode;

    /**
     * 立案时间
     */
    @TableField("register_time")
    private LocalDateTime registerTime;

    /**
     * 出动时间
     */
    @TableField("dispatch_time")
    private LocalDateTime dispatchTime;

    /**
     * 案发地址
     */
    @TableField("incident_addr")
    private String incidentAddr;

    /**
     * 案发地址110
     */
    @TableField("incident_addr_police")
    private String incidentAddrPolice;

    /**
     * 灾害场所
     */
    @TableField("disaster_site")
    private String disasterSite;

    /**
     * 灾害原因
     */
    @TableField("disaster_reason")
    private String disasterReason;

    /**
     * 重点单位类型（100 一体化重点单位；200 上海重点单位)
     */
    @TableField("key_unit_type")
    private String keyUnitType;

    /**
     * 报送消防机构代码，总队下发的调派单使用
     */
    @TableField("submitted_firee_code")
    private String submittedFireeCode;

    /**
     * 灾情来源消防机构代码，总队下发调派单使用
     */
    @TableField("disaster_original")
    private String disasterOriginal;

    /**
     * 主管机构编号
     */
    @TableField("manager_institution_number")
    private String managerInstitutionNumber;

    /**
     * 主管机构名称
     */
    @TableField("manager_institution_name")
    private String managerInstitutionName;

    /**
     * 行政区域编号
     */
    @TableField("administrative_region_number")
    private String administrativeRegionNumber;

    /**
     * 行政区域编号
     */
    @TableField("administrative_region")
    private String administrativeRegion;

    /**
     * 处置对象
     */
    @TableField("handle_object")
    private String handleObject;

    /**
     * 处置对象代码
     */
    @TableField("handle_object_code")
    private String handleObjectCode;

    /**
     * 处置对象简称
     */
    @TableField("handle_object_name")
    private String handleObjectName;

    /**
     * 是否高架派车0：否；1：是
     */
    @TableField("is_elevated_car")
    private String isElevatedCar;

    /**
     * 下达时间
     */
    @TableField("release_time")
    private LocalDateTime releaseTime;

    /**
     * 接收时间
     */
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    /**
     * 战斗单位ID
     */
    @TableField("battle_unit_id")
    private String battleUnitId;

    /**
     * 作战编号
     */
    @TableField("combat_number")
    private String combatNumber;

    /**
     * 战斗员人数
     */
    @TableField("battle_unit_person_number")
    private Integer battleUnitPersonNumber;

    /**
     * 频道标志
     */
    @TableField("channel_flag")
    private Integer channelFlag;

    /**
     * 出车总数
     */
    @TableField("out_car_number")
    private Integer outCarNumber;

    /**
     * 注意事项
     */
    @TableField("note")
    private String note;

    /**
     * 缺水区域（0：否；1：是）
     */
    @TableField("hydropenia_region")
    private Boolean hydropeniaRegion;

    /**
     * 敏感区域（0：否；1：是）
     */
    @TableField("sensitive_region")
    private Boolean sensitiveRegion;

    /**
     * 严寒影响（0：否；1：是）
     */
    @TableField("severe_cold_region")
    private Boolean severeColdRegion;

    /**
     * 重大危险源（0：否；1：是）
     */
    @TableField("major_danger_region")
    private Boolean majorDangerRegion;

    /**
     * 大风影响（0：否；1：是）
     */
    @TableField("gale_region")
    private Boolean galeRegion;

    /**
     * 涉外区域（0：否；1：是）
     */
    @TableField("sw_region")
    private Boolean swRegion;

    /**
     * 重点时段（0：否；1：是）
     */
    @TableField("key_period")
    private Boolean keyPeriod;

    /**
     * 调度标志(0:未调度 1:已调度)
     */
    @TableField("dispatch_flag")
    private Boolean dispatchFlag;

    /**
     * 到达现场时间
     */
    @TableField("reach_scene_time")
    private LocalDateTime reachSceneTime;

    /**
     * 战斗展开时间
     */
    @TableField("battle_unfold_time")
    private LocalDateTime battleUnfoldTime;

    /**
     * 接收命令时间
     */
    @TableField("receive_command_time")
    private LocalDateTime receiveCommandTime;

    /**
     * 到场出水时间
     */
    @TableField("reach_scene_out_water_time")
    private LocalDateTime reachSceneOutWaterTime;

    /**
     * 火势控制时间
     */
    @TableField("fire_control_time")
    private LocalDateTime fireControlTime;

    /**
     * 基本扑灭时间
     */
    @TableField("basic_extinction_time")
    private LocalDateTime basicExtinctionTime;

    /**
     * 回执时间
     */
    @TableField("receipt_time")
    private LocalDateTime receiptTime;

    /**
     * 归队时间
     */
    @TableField("regression_time")
    private LocalDateTime regressionTime;

    /**
     * 烟雾情况代码
     */
    @TableField("smoke_situation_code")
    private String smokeSituationCode;

    /**
     * 气象信息描述
     */
    @TableField("weather_desc")
    private String weatherDesc;

    /**
     * 建筑结构类型代码
     */
    @TableField("build_type_code")
    private String buildTypeCode;

    /**
     * 燃烧楼层
     */
    @TableField("burning_floor")
    private Integer burningFloor;

    /**
     * 燃烧面积
     */
    @TableField("burning_area")
    private Integer burningArea;

    /**
     * 楼房层数
     */
    @TableField("floor_number")
    private Integer floorNumber;

    /**
     * 保护面积
     */
    @TableField("protect_area")
    private Integer protectArea;

    /**
     * 人员被困数
     */
    @TableField("person_trapped_number")
    private String personTrappedNumber;

    /**
     * 受伤人数
     */
    @TableField("injured_person_number")
    private Integer injuredPersonNumber;

    /**
     * 救出人数
     */
    @TableField("extricate_person_number")
    private Integer extricatePersonNumber;

    /**
     * 疏散人数
     */
    @TableField("evacuate_person_number")
    private Integer evacuatePersonNumber;

    /**
     * 群众死亡人数
     */
    @TableField("mass_deaths_number")
    private Integer massDeathsNumber;

    /**
     * 部队受伤人数
     */
    @TableField("troops_injured_number")
    private Integer troopsInjuredNumber;

    /**
     * 部队死亡人数
     */
    @TableField("troops_deaths_number")
    private Integer troopsDeathsNumber;

    /**
     * 删除人员
     */
    @TableField("delete_person")
    private String deletePerson;

    /**
     * 删除坐席
     */
    @TableField("delete_seats")
    private String deleteSeats;

    /**
     * 传输状态
     */
    @TableField("transmission_status")
    private Integer transmissionStatus;

    /**
     * 合并状态̬
     */
    @TableField("merge_status")
    private Integer mergeStatus;

    /**
     * lhtx
     */
    @TableField("lhtx")
    private String lhtx;

    /**
     * XFBZ
     */
    @TableField("XFBZ")
    private Integer xfbz;

    @TableField("GIS_Y")
    private String gisY;

    @TableField("GIS_X")
    private String gisX;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 案件类型
     */
    @TableField("case_type")
    private String caseType;

    /**
     * 案件类型代码
     */
    @TableField("case_type_code")
    private String caseTypeCode;

    /**
     * 案件等级
     */
    @TableField("case_grade")
    private String caseGrade;

    /**
     * 案件等级代码
     */
    @TableField("case_grade_code")
    private String caseGradeCode;

    /**
     * 案件描述
     */
    @TableField("case_desc")
    private String caseDesc;

    /**
     * 案件性质
     */
    @TableField("case_nature")
    private String caseNature;

    /**
     * 案件性质代码
     */
    @TableField("case_nature_code")
    private Integer caseNatureCode;

    /**
     * 案件状态̬
     */
    @TableField("case_status")
    private String caseStatus;

    /**
     * 案件状代码
     */
    @TableField("case_status_code")
    private String caseStatusCode;

    /**
     * 案件来源
     */
    @TableField("case_original")
    private String caseOriginal;

    /**
     * 案件来源代码
     */
    @TableField("case_original_code")
    private Integer caseOriginalCode;

    /**
     * JJTS
     */
    @TableField("JJTS")
    private String jjts;

    /**
     * 财产损失
     */
    @TableField("property_loss")
    private String propertyLoss;

    /**
     * 补充信息
     */
    @TableField("supplement_info")
    private String supplementInfo;

    /**
     * 记录状态̬
     */
    @TableField("status")
    private Integer status;

    /**
     * 保留案件编号
     */
    @TableField("reserve_case_number")
    private String reserveCaseNumber;

    /**
     * 数据域
     */
    @TableField("sjy")
    private String sjy;

    /**
     * 是否已发送:0-未发送;1-已发送
     */
    @TableField("is_send")
    private Boolean isSend;

    /**
     * 是否推送:0-未推送;1-已推送
     */
    @TableField("is_push")
    private Boolean isPush;

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

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
