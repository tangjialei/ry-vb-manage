package com.street.one.manage.job.handler;

import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.utils.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.handler
 * @ClassName: QuartzDisallowConcurrentExecution
 * @Author: tjl
 * @Description: 定时任务处理（禁止并发执行）
 * @Date: 2024/7/3 15:12
 * @modified modify person name
 * @Version: 1.0
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, BasisJobEntity jobEntity) throws Exception {
        JobInvokeUtil.invokeTargetMethod(jobEntity);
    }
}
