package com.street.one.manage.job.manager;

import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.constants.ScheduleConstants;
import com.street.one.manage.common.enums.TaskCodeEnum;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.handler.QuartzDisallowConcurrentExecution;
import com.street.one.manage.job.handler.QuartzJobExecution;
import com.street.one.manage.job.utils.CronUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.manager
 * @ClassName: QuartzManager
 * @Author: tjl
 * @Description: 调度任务管理类
 * @Date: 2024/7/3 16:11
 * @modified modify person name
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class QuartzManager {

    /***
     * 调度任务类 quartz提供的
     */
    private final Scheduler scheduler;

    /***
     *清除调度任务
     */
    public void clearScheduler(){
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到quartz任务类
     *
     * @param jobEntity 计划任务
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(BasisJobEntity jobEntity) {
        return jobEntity.getConcurrent() ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /***
     * 构建任务触发对象
     * @param jobId 计划任务id
     * @param jobGroup 计划任务组
     * @return
     */
    private static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public void createScheduleJob(BasisJobEntity job) throws SchedulerException, TaskException {
        //得到quartz任务类
        Class<? extends Job> jobClass = getQuartzJobClass(job);

        // 构建job信息
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        // 判断任务是否过期
        if (StringUtil.isNotNull(CronUtils.getNextExecution(job.getCronExpression()))) {
            // 执行调度任务
            scheduler.scheduleJob(jobDetail, trigger);
        }

        // 暂停的任务
        if (job.getRunStatus().equals(CommonConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(getJobKey(jobId, jobGroup));
        }
    }

    /***
     * 构建任务键对象
     * @param jobId 计划任务id
     * @param jobGroup 计划任务组
     * @return
     */
    private JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 设置定时任务策略
     */
    private CronScheduleBuilder handleCronScheduleMisfirePolicy(BasisJobEntity job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            //默认
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            //立即触发执行
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            //触发一次执行
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            //不触发立即执行
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException( String.format("计划执行错误策略:%s,cron调度任务不能调度",job.getMisfirePolicy()), TaskCodeEnum.CONFIG_ERROR);
        }
    }

}
