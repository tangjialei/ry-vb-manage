package com.street.one.manage.job.handler;

import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.utils.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.utils
 * @ClassName: QuartzJobExecution
 * @Author: tjl
 * @Description: 定时任务处理（允许并发执行）
 * @Date: 2024/7/3 15:11
 * @modified modify person name
 * @Version: 1.0
 */
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, BasisJobEntity jobEntity) throws Exception {
        JobInvokeUtil.invokeTargetMethod(jobEntity);
    }
}
