package com.street.one.manage.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: Threads
 * @Author: tjl
 * @Description: 线程相关工具类
 * @Date: 2024/6/24 10:19
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public class ThreadUtils {

    /***
     * 最大等待时间
     */
    private final static Long TIMEOUT = 120L;

    /***
     * 停止线程池
     * @param pool
     */
    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                    if (!pool.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                        log.info("Pool did not terminate");
                    }
                }
            }
            catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     */
    public static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            }
            catch (CancellationException ce) {
                t = ce;
            }
            catch (ExecutionException ee) {
                t = ee.getCause();
            }
            catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            log.error(t.getMessage(), t);
        }
    }

}
