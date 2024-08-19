package com.street.one.manage.web.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.config
 * @ClassName: AsyncTaskPoolConfig
 * @Author: tjl
 * @Description: 异步任务线程池配置
 * @Date: 2023/7/25 12:21
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
public class AsyncTaskPoolConfig implements AsyncConfigurer {

    /**
     * 参数初始化
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数量大小
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 线程池最大容纳线程数
     */
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 阻塞队列
     */
    private static final int WORK_QUEUE = 20;
    /**
     * 线程空闲后的存活时长
     */
    private static final int KEEP_ALIVE_TIME = 30;

    @Override
    @Bean("asyncTaskExecutor")
    @Primary
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        //等待队列
        threadPoolTaskExecutor.setQueueCapacity(WORK_QUEUE);
        //线程前缀
        threadPoolTaskExecutor.setThreadNamePrefix("Async_Task_Pool_");
        //线程池维护线程所允许的空闲时间,单位为秒
        threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        // 线程池对拒绝任务(无线程可用)的处理策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置线程池关闭的时候等待所有任务都完成后，再继续销毁其他的Bean,这样这些异步任务的销毁就会先于数据库连接池对象的销毁
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间,如果超过这个时间还没有销毁就强制销毁以确保应用最后能够被关,而不是阻塞住
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}

