package com.street.one.manage.middleware.zd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;

import com.street.one.manage.common.core.domain.BaseLogEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 接处警接收推送日志
 * </p>
 *
 * @author tjl
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zd_receiving_alarm_log")
public class ZdReceivingAlarmLogEntity extends BaseLogEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日志代码,全局唯一
     */
    @TableField("code")
    private String code;

    /**
     * 业务类型（0=其它,1=新增,2=修改,3=删除,4=导出,5=导入,6=强退,7=拉取）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 请求URL
     */
    @TableField("url")
    private String url;

    /**
     * 主机地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField("request_param")
    private String requestParam;

    /**
     * 响应参数
     */
    @TableField("response_param")
    private String responseParam;

    /***
     * 执行状态（init-初始,success-正常 error-失败）
     */
    @TableField("execute_status")
    private String executeStatus;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

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
