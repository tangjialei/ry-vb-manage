
package com.street.one.manage.common.resp;

import lombok.Data;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.resp
 * @ClassName: CoordinateReso
 * @Author: tjl
 * @Description:  第三方地址转换坐标类
 * @Date: 2023/8/7 19:32
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class GaoDeGeoCodesResp {
    /***
     * 通过中文地址获取到的经纬度 以逗号分割
     * "location":"121.454750,31.168241"
     */
    private String location;
}