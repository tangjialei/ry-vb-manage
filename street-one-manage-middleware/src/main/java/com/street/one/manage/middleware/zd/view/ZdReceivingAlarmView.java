package com.street.one.manage.middleware.zd.view;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.view
 * @ClassName: ZdReceivingAlarmView
 * @Author: tjl
 * @Description: 总队警情视图
 * @Date: 2024/5/21 10:27
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class ZdReceivingAlarmView {

    /***
     * 案件id
     */
    @JSONField(name = "ID")
    private String receivingAlarmId;

    /***
     * 主管支队
     */
    @JSONField(name = "主管支队")
    private String  mainUnitName;

    /***
     * 案件时间段
     */
    @JSONField(name = "案件时间段")
    private String caseTimePeriod;

    /***
     * 区域
     */
    @JSONField(name = "区域")
    private String region;

    /***
     * 案件类型
     */
    @JSONField(name = "案件类型")
    private String caseType;

    /***
     * 案件类型代码
     */
    @JSONField(name = "案件类型代码")
    private String caseTypeCode;

    /**
     * 案发地址
     */
    @JSONField(name = "案发地址")
    private String incidentAddr;

    /**
     * 立案时间
     */
    @JSONField(name = "立案时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

    /***
     * 立案日期
     */
    @JSONField(name = "立案日期",format = "yyyy-MM-dd")
    private LocalDate registerDate;

    /**
     * 处置对象
     */
    @JSONField(name = "处置对象")
    private String handleObject;

    /***
     * 案件等级
     */
    @JSONField(name = "案件等级")
    private String caseGrade;

    /***
     * 主管中队名称
     */
    @JSONField(name = "主管中队")
    private String  mainCenterUnitName;

    /**
     * 区/县
     */
    @JSONField(name = "区县")
    private String county;

    /***
     * 案件编号
     */
    @JSONField(name = "案件编号")
    private String caseCode;

    /***
     * 案件状态名称
     */
    @JSONField(name = "案件状态")
    private String caseStatusName;

    /**
     * 报警时间
     */
    @JSONField(name = "报警时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime alarmTime;

    /**
     * 案件性质
     */
    @JSONField(name = "案件性质")
    private String caseNature;

    /***
     * 补充信息
     */
    @JSONField(name = "补充信息")
    private String caseDesc;

    /***
     * 出动车辆数
     */
    @JSONField(name = "出动车辆")
    private int dispatchCarCount;

    /***
     * 案件区域
     */
    @JSONField(name = "案件区域")
    private String caseRegion;


    /****
     * 通知时间
     */
    @JSONField(name = "通知时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noticeTime;


    /***
     * 到场时间
     */
    @JSONField(name = "到场时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    /****
     * 返队时间
     */
    @JSONField(name = "返队时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime returnTime;


    /****
     * 出水时间
     */
    @JSONField(name = "出水时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outWaterTime;


    /****
     * 控制时间
     */
    @JSONField(name = "控制时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime controlTime;


    /****
     * 熄灭时间
     */
    @JSONField(name = "熄灭时间",format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putOutTime;



    /**
     * 经度 GIS_X
     */
    @JSONField(name = "GIS_X")
    private String longitude;

    /**
     * 纬度 GIS_Y
     */
    @JSONField(name = "GIS_Y")
    private String latitude;

    /**
     * 城建坐标x
     */
    private String cityX;

    /**
     * 城建坐标y
     */
    private String cityY;

    /**
     * 数据域
     */
    @JSONField(name = "SJC")
    private String sjy;


    /****
     *  车辆集合
     */
    private List<ZdReceivingAlarmCarView> carViews;

}
