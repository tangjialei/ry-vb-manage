package com.street.one.manage.common.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.resp
 * @ClassName: CoordinateReso
 * @Author: tjl
 * @Description:  通用坐标返回类
 * @Date: 2023/8/7 19:32
 * @modified modify person name
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateResp implements Serializable {
    private static final long serialVersionUID = -7990245602907090725L;
    /***
     * 城运经度
     */
    private double longitude;
    /***
     * 城运纬度
     */
    private double latitude;
}
