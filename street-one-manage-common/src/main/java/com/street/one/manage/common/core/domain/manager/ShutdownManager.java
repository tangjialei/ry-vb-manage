package com.street.one.manage.common.core.domain.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.manager
 * @ClassName: ShutdownManager
 * @Author: tjl
 * @Description: 关闭后台线程
 * @Date: 2024/6/24 14:13
 * @modified modify person name
 * @Version: 1.0
 */
@Component
@Slf4j
public class ShutdownManager {


    /***
     * 服务器停止的时候执行销毁线程池
     */
    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager()
    {
        try {
            log.info("====停止后台任务线程池====");
            AsyncManager.asyncManager().shutdown();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
