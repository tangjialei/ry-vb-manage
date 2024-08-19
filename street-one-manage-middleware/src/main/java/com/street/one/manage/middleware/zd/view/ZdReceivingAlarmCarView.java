package com.street.one.manage.middleware.zd.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.third.view
 * @ClassName: ZdReceivingAlarmCarView
 * @Author: tjl
 * @Description: 总队视图作战车辆视图数据
 * @Date: 2024/5/21 11:15
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class ZdReceivingAlarmCarView {

    /***
     * 主键id
     */
    @JSONField(name = "ID")
    private String id;

    /***
     * 案件id
     */
    @JSONField(name = "案件ID")
    private String receivingAlarmId;


    /**
     * 消防机构id
     */
    @JSONField(name = "消防机构ID")
    private String fireInstitutionId;

    /***
     * 处警纪录ID
     */
    @JSONField(name = "处警纪录ID")
    private String policeRecord;

    /***
     * 出动车辆id
     */
    @JSONField(name = "出动车辆ID")
    private String dispatchCarId;

    /**
     * 数据域
     */
    @JSONField(name = "SJC")
    private String sjy;


    /****
     * 通知时间
     */
    @JSONField(name = "通知时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String noticeTime;

    /***
     * 出动时间
     */
    @JSONField(name = "出动时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String dispatchTime;

    /***
     * 到场时间
     */
    @JSONField(name = "到场时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String arrivalTime;

    /****
     * 返队时间
     */
    @JSONField(name = "返队时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String returnTime;

    /**
     * 车辆简称
     */
    @JSONField(name = "车辆简称")
    private String carJc;

    /****
     * 电台呼号
     */
    @JSONField(name = "电台呼号")
    private String radioStationNum;

    /***
     * 指挥员
     */
    @JSONField(name = "指挥员")
    private String commander;

    /***
     * 驾驶员
     */
    @JSONField(name = "驾驶员")
    private String driver;

    /***
     * 通讯员
     */
    @JSONField(name = "通讯员")
    private String correspondent;

    /***
     * 随车人员
     */
    @JSONField(name = "随车人员")
    private String followCarPerson;

    /***
     * 作战功能代码
     */
    @JSONField(name = "作战功能代码")
    private String fightCode;

    /***
     * 作战功能名称
     */
    @JSONField(name = "作战功能名称")
    private String fightName;

    /**
     * 消防机构简称
     */
    @JSONField(name = "消防机构简称")
    private String fireInstitutionNameJc;

    /***
     * 消防机构缩写
     */
    @JSONField(name = "消防机构缩写")
    private String fireInstitutionNameSx;

    /***
     * 车牌号
     */
    @JSONField(name = "CPHM")
    private String carNumber;

}
