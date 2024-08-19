package com.street.one.manage.middleware.zd.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.resp
 * @ClassName: ZdReceivingAlarmMainInfoCountResp
 * @Author: tjl
 * @Description: （海致+总队视图）统计警情统一返回结果集
 * @Date: 2024/7/8 17:36
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class ZdReceivingAlarmMainDetailsCountResp implements Serializable {

    private static final long serialVersionUID = 964231408041445960L;

    /**
     * 计算维度
     */
    private String calculateType;

    /**
     * 计算年月
     */
    private LocalDate calculateYears;

    /**
     * 警情总数
     */
    private Integer receivingAlarmCount;

    /**
     * 警情环比
     */
    private BigDecimal alarmRingCompare;

    /**
     * 警情同比
     */
    private BigDecimal alarmSameCompare;

    /**
     * 火灾统计
     */
    private Integer fireCount;

    /**
     * 火灾环比
     */
    private BigDecimal fireRingCompare;

    /**
     * 火灾同比
     */
    private BigDecimal fireSameCompare;

    /**
     * 抢险统计
     */
    private Integer salvageCount;

    /**
     * 抢险环比
     */
    private BigDecimal salvageRingCompare;

    /**
     * 抢险同比
     */
    private BigDecimal salvageSameCompare;

    /**
     * 社救统计
     */
    private Integer societyHelpCount;

    /**
     * 社救环比
     */
    private BigDecimal societyHelpRingCompare;

    /**
     * 社救同比
     */
    private BigDecimal societyHelpSameCompare;

    /**
     * 平均出动时间
     */
    private String avgDispatchDuration;

    /**
     * 平均到场时长
     */
    private String avgReachDuration;

    /**
     * 平均出水时长
     */
    private String avgOutWaterDuration;

    /**
     * 平均处置时长
     */
    private String avgHandleDuration;


}
