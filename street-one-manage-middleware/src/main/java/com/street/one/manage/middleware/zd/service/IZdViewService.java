package com.street.one.manage.middleware.zd.service;

import com.street.one.manage.middleware.zd.view.ZdReceivingAlarmCarView;
import com.street.one.manage.middleware.zd.view.ZdReceivingAlarmView;

import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.service
 * @ClassName: IZdViewService
 * @Author: tjl
 * @Description: TODO
 * @Date: 2024/7/8 17:53
 * @modified modify person name
 * @Version: 1.0
 */
public interface IZdViewService {


    /***
     * 根据警情id获取总队的案件信息及车辆丶人员
     * @param params sql参数
     * @return
     */
    ZdReceivingAlarmView getZdReceivingAlarmList(List<Object> params);


    /***
     * 根据案件id，获取出动车辆，会存在一个警情多台车辆的情况
     * @param receivingAlarmId
     * @return
     */
    List<ZdReceivingAlarmCarView> getZdCarViewByReceivingAlarmId(String receivingAlarmId);


}
