package com.street.one.manage.middleware.zd.service.impl;

import com.street.one.manage.middleware.zd.service.IZdViewService;
import com.street.one.manage.middleware.zd.view.ZdReceivingAlarmCarView;
import com.street.one.manage.middleware.zd.view.ZdReceivingAlarmView;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.middleware.zd.service.impl
 * @ClassName: ViewServiceImpl
 * @Author: tjl
 * @Description: 总队视图接口 服务层
 * @Date: 2024/7/8 17:54
 * @modified modify person name
 * @Version: 1.0
 */
public class ZdViewServiceImpl implements IZdViewService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public ZdReceivingAlarmView getZdReceivingAlarmList(List<Object> params) {
        //根据id查询警情信息
        String querySql = "SELECT * FROM V_ZHCXDYAJ_ALL_XH WHERE 立案时间 BETWEEN ? AND ? ORDER BY SJC DESC;";
        ZdReceivingAlarmView zdReceivingAlarmView = null;
        List<Map<String, Object>> maps = Lists.newArrayList();
        try{
            maps = jdbcTemplate.queryForList(querySql, params);
            //basisThirdApiLogService.writeApiLog("getZdReceivingAlarmInfo",querySql, receivingAlarmId, JSON.toJSONString(map), true);
        }catch (Exception e){
            //basisThirdApiLogService.writeApiLog("getZdReceivingAlarmInfo",querySql, receivingAlarmId,e.getMessage(), false);
            e.printStackTrace();
        }
//        if(!CollectionUtils.isEmpty(map)){
//            zdReceivingAlarmView = JSON.parseObject(JSON.toJSONString(map), ZdReceivingAlarmView.class);
//            if(null != zdReceivingAlarmView){
//                String longitude = zdReceivingAlarmView.getLongitude();
//                String latitude = zdReceivingAlarmView.getLatitude();
//                if(!StringUtil.isEmptyOrNull(longitude) || Integer.parseInt(longitude) > 0){
//                    //CGCS2000转坐标转城运
//                    CoordinateResp coordinateResp = CoordinateConvertUtils.cgs2000ToCity(Double.parseDouble(longitude)
//                            ,Double.parseDouble(latitude));
//                    if(null != coordinateResp){
//                        zdReceivingAlarmView.setCityX(String.valueOf(coordinateResp.getLongitude()));
//                        zdReceivingAlarmView.setCityY(String.valueOf(coordinateResp.getLatitude()));
//                    }
//                }
//
//            }
//        }
        return zdReceivingAlarmView;
    }

    @Override
    public List<ZdReceivingAlarmCarView> getZdCarViewByReceivingAlarmId(String receivingAlarmId) {
        return null;
    }
}
