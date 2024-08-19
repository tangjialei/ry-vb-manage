package com.street.one.manage.common.core.domain.manager;

import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.common.utils.ThreadUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.manager
 * @ClassName: AsyncManager
 * @Author: tjl
 * @Description: 异步管理器
 * @Date: 2024/6/24 10:14
 * @modified modify person name
 * @Version: 1.0
 */
public class AsyncManager {


    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = (ScheduledExecutorService) SpringApplicationContext.getBean("scheduledExecutorService");

    private AsyncManager(){}

    /***
     * 单例模式
     */
    private static AsyncManager asyncManager = new AsyncManager();

    public static AsyncManager asyncManager()
    {
        return asyncManager;
    }


    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executor.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        ThreadUtils.shutdownAndAwaitTermination(executor);
    }


}
