package com.street.one.manage.middleware.zd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmMainEntity;
import com.street.one.manage.middleware.zd.mapper.ZdReceivingAlarmMainMapper;
import com.street.one.manage.middleware.zd.resp.ZdReceivingAlarmMainDetailsCountResp;
import com.street.one.manage.middleware.zd.service.IZdReceivingAlarmMainService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接处警_案件信息(迪艾斯推送的数据记录) 服务实现类
 * </p>
 *
 * @author tjl
 * @since 2024-07-04
 */
@Service
public class ZdReceivingAlarmMainServiceImpl extends ServiceImpl<ZdReceivingAlarmMainMapper, ZdReceivingAlarmMainEntity> implements IZdReceivingAlarmMainService {




    @Override
    public ZdReceivingAlarmMainDetailsCountResp countReceivingAlarmMainInfo(String calculateType) {
        return null;
    }
}
