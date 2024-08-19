package com.street.one.manage.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.manager.QuartzManager;
import com.street.one.manage.job.mapper.BasisJobMapper;
import com.street.one.manage.job.service.IBasisJobService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author tjl
 * @since 2024-07-03
 */
@Service
@RequiredArgsConstructor
public class BasisJobServiceImpl extends ServiceImpl<BasisJobMapper, BasisJobEntity> implements IBasisJobService {

    private final QuartzManager quartzManager;

    /***
     * 启动加载数据库的计划任务
     * @throws SchedulerException
     * @throws TaskException
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        //清理调度任务
        quartzManager.clearScheduler();
        List<BasisJobEntity> jobList = this.baseMapper.selectList(new LambdaQueryWrapper<BasisJobEntity>().eq(BasisJobEntity::getStatus,"ST002"));
        for (BasisJobEntity job : jobList) {
            //创建计划任务
            quartzManager.createScheduleJob(job);
        }
    }

}
