package com.street.one.manage.middleware.zd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmMainEntity;
import com.street.one.manage.middleware.zd.resp.ZdReceivingAlarmMainDetailsCountResp;

/**
 * <p>
 * 接处警_案件信息(迪艾斯推送的数据记录) 服务类
 * </p>
 *
 * @author tjl
 * @since 2024-07-04
 */
public interface IZdReceivingAlarmMainService extends IService<ZdReceivingAlarmMainEntity> {


    /***
     * 统计（海致+总队视图）警情信息
     * @param calculateType
     * @return
     */
    ZdReceivingAlarmMainDetailsCountResp countReceivingAlarmMainInfo(String calculateType);


}
