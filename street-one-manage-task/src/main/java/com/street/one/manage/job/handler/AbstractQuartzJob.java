package com.street.one.manage.job.handler;

import com.street.one.manage.common.constants.ScheduleConstants;
import com.street.one.manage.common.enums.LogStatus;
import com.street.one.manage.common.utils.DateUtil;
import com.street.one.manage.common.utils.ExceptionUtil;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.entity.BasisJobLogEntity;
import com.street.one.manage.job.service.IBasisJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.handler
 * @ClassName: AbstractQuartzJob
 * @Author: tjl
 * @Description: 抽象quartz调用
 * @Date: 2024/7/3 14:39
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();


    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        //计划任务对象
        BasisJobEntity jobEntity = new BasisJobEntity();
        BeanUtils.copyProperties(jobContext.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES),jobEntity);
        try {
            //执行前
            beforeHandler();
            //执行具体方法，子类实现
            doExecute(jobContext, jobEntity);
            //执行后置处理
            afterHandler(jobEntity, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            afterHandler(jobEntity, e);
        }
    }

    /**
     * 执行前
     */
    protected void beforeHandler(){
        threadLocal.set(DateUtil.getNow());
    }

    /**
     * 执行后
     * @param jobEntity 计划任务对象
     */
    protected void afterHandler(BasisJobEntity jobEntity, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        //记录日志
        final BasisJobLogEntity jobLogEntity = new BasisJobLogEntity();
        jobLogEntity.setJobName(jobEntity.getJobName());
        jobLogEntity.setJobGroup(jobEntity.getJobGroup());
        jobLogEntity.setInvokeTargetMethod(jobEntity.getInvokeTargetMethod());
        //运行时间
            long runMs = DateUtil.getNow().getTime() - startTime.getTime();
        jobLogEntity.setJobLogMessage(jobLogEntity.getJobName() + " 总共耗时：" + runMs + "毫秒");
        //默认日志状态是成功的
        jobLogEntity.setStatus(LogStatus.SUCCESS.getType());
        if (e != null) {
            String errorMsg = StringUtil.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            jobLogEntity.setExceptionInfo(errorMsg);
            jobLogEntity.setStatus(LogStatus.ERROR.getType());
        }
        jobLogEntity.setCreateTime(LocalDateTime.now());
        // 记录计划任务执行日志
        SpringApplicationContext.getBean(IBasisJobLogService.class).save(jobLogEntity);
    }


    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param jobEntity 计划任务对象
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, BasisJobEntity jobEntity) throws Exception;
}
