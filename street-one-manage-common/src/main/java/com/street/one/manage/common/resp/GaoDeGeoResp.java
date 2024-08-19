
package com.street.one.manage.common.resp;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.resp
 * @ClassName: CoordinateReso
 * @Author: tjl
 * @Description:  第三方地址转换坐标，总对象
 * @Date: 2023/8/7 19:32
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class GaoDeGeoResp {
    /***
     * 状态码
     */
    private String status;
    /***
     * ok=成功
     */
    private String info;
    /***
     * 错误码
     */
    private String infocode;
    /***
     * 数量
     */
    private String count;
    /***
     * 地址详情
     */
    private List<GaoDeGeoCodesResp> geocodes;
}