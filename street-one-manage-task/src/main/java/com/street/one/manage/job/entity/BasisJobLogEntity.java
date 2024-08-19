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
 * 定时任务调度日志表
 * </p>
 *
 * @author tjl
 * @since 2024-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("basis_job_log")
public class BasisJobLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务日志ID
     */
    @TableId(value = "job_log_id", type = IdType.AUTO)
    private Integer jobLogId;

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
     * 调用目标方法及参数,这里指的是service方法
     */
    @TableField("invoke_target_method")
    private String invokeTargetMethod;

    /**
     * 日志信息
     */
    @TableField("job_log_message")
    private String jobLogMessage;

    /**
     * 执行状态（success-正常 error-失败）
     */
    @TableField("status")
    private String status;

    /**
     * 异常信息
     */
    @TableField("exception_info")
    private String exceptionInfo;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
