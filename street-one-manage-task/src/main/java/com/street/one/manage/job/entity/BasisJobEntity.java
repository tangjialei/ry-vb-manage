package com.street.one.manage.job.entity;

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
 * 定时任务调度表
 * </p>
 *
 * @author tjl
 * @since 2024-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_job")
public class BasisJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @TableField("job_group")
    private String jobGroup;

    /**
     * 调用目标方法及参数，这里指的是service方法
     */
    @TableField("invoke_target_method")
    private String invokeTargetMethod;

    /**
     * cron执行表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 计划执行错误策略（1-立即执行 2-执行一次 3-放弃执行）
     */
    @TableField("misfire_policy")
    private String misfirePolicy;

    /**
     * 是否并发执行（1-允许,0-禁止）
     */
    @TableField("concurrent")
    private Boolean concurrent;

    /***
     * 运行状态（0-正常 1-暂停）
     */
    @TableField("run_status")
    private String runStatus;

    /**
     * 自定义过滤数据范围条件参数，如：sex="1" AND  reg_time BETWEEN  "2018-12-11 00:00:00” AND  "2019-05-11 23:59:59"
     */
    @TableField("filter_params")
    private String filterParams;

    /**
     * ST002:启用，ST001：停用
     */
    @TableField("status")
    private String status;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

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
