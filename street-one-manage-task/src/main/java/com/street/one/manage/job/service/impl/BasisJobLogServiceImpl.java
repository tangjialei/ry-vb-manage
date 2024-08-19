package com.street.one.manage.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.street.one.manage.job.entity.BasisJobLogEntity;
import com.street.one.manage.job.mapper.BasisJobLogMapper;
import com.street.one.manage.job.service.IBasisJobLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author tjl
 * @since 2024-07-03
 */
@Service
public class BasisJobLogServiceImpl extends ServiceImpl<BasisJobLogMapper, BasisJobLogEntity> implements IBasisJobLogService {

}
