package com.street.one.manage.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.resp.CoordinateResp;
import com.street.one.manage.common.resp.GaoDeGeoCodesResp;
import com.street.one.manage.common.resp.GaoDeGeoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.utils
 * @ClassName: CoordinateConvertUtils
 * @Author: tjl
 * @Description: 城运坐标转换
 * @Date: 2023/8/7 19:30
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
public class CoordinateConvertUtils {
    private final static String CITY_URL = "http://31.0.37.231:8080/coordtransm1ws/wstrans";
    private final static String CITY_URL2 = "http://31.0.37.231:8080/coordtransm1sw/swtrans";

    private static final double PI = 3.1415926535897932384626;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;
    public static double pi = 3.1415926535897932384626;

    /***
     * 返回状态码
     */
    private static final String RESULT_SUCCESS = "OK";

    /***
     * CGCS2000转坐标转城运
     * @param longitude
     * @param latitude
     * @return
     */
    public static CoordinateResp cgs2000ToCity(double longitude, double latitude) {
        String rqq = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><WGS84BLH2SH xmlns=\"SHCoordTransM1WS\"><arg0 xmlns=\"\">@@arg1</arg0><arg1 xmlns=\"\">@@arg2</arg1><arg2 xmlns=\"\">0</arg2></WGS84BLH2SH></soap:Body></soap:Envelope>";
        latitude = latitude * Math.PI / 180;
        longitude = longitude * Math.PI / 180;
        rqq = rqq.replaceAll("@@arg1", String.valueOf(latitude));
        rqq = rqq.replaceAll("@@arg2", String.valueOf(longitude));
        System.out.println(rqq);
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "text/xml;charset=utf-8");
        double y = 0.0;
        double x = 0.0;
        try{
            String result = HttpUtil.createPost(CITY_URL).addHeaders(headers).body(rqq).execute(false).body();
            log.info("result: " + result);
            Matcher m = Pattern.compile("[-+]?\\d+\\.\\d+").matcher(result);
            for (int i = 1; i <= 3; i++) {
                m.find();
                if (i == 2) {
                    y = Double.parseDouble(m.group());
                }
                if (i == 3) {
                    x = Double.parseDouble(m.group());
                }
            }
            log.info("cgs2000 to city::->longitude:{},latitude:{}", x,y);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return new CoordinateResp(x, y);
    }

    /***
     * wgs84 转换 Gcj02
     * @param lon
     * @param lat
     * @return
     */
    public static CoordinateResp wgs84ToGcj02(double lon, double lat) {
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new CoordinateResp(mgLon, mgLat);
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

    /***
     * 地址转换高德地图坐标,GCJ-02坐标系
     * @param address
     * @return
     * @throws Exception
     * <p>
     *     key = e63cdf0fcd7cf1d84086241335bd56f5
     *     这个是我个人的，如有需要可以更换
     *     kel = cb61d4446f0c322f0c32ad412e234231 公司的先不用
     * <p/>
     */
    public static CoordinateResp addressToGaoDe(String address){
        String url = "https://restapi.amap.com/v3/geocode/geo?key=e63cdf0fcd7cf1d84086241335bd56f5&address=@@address&city=shanghai";
        url = url.replaceAll("@@address", address);
        String result = HttpUtil.get(url);
        log.info("result address::{}",result);
        //返回结果集
        GaoDeGeoResp gaoDeGeoResp = JSONUtils.parse(result, GaoDeGeoResp.class);
        log.info("result address convert::{}", JSON.toJSONString(gaoDeGeoResp));
        if(null != gaoDeGeoResp && RESULT_SUCCESS.equals(gaoDeGeoResp.getInfo())){
            List<GaoDeGeoCodesResp> geoCodes = gaoDeGeoResp.getGeocodes();
            if(!CollectionUtils.isEmpty(geoCodes)){
                String[] ary= geoCodes.get(0).getLocation().split(",");
                return new CoordinateResp(Double.parseDouble(ary[0]),Double.parseDouble(ary[1]));
            }
        }
        return null;
    }

    /***
     * cgs2000 通过高德转 wgs84，很精准，通过gps来转
     * API：https://lbs.amap.com/api/webservice/guide/api/convert
     * @param longitude
     * @param latitude
     * @return
     */
    public static CoordinateResp cgs2000ToWgs84(Double longitude, Double latitude){
        CoordinateResp coordinateResp = new CoordinateResp();
        String url = "https://restapi.amap.com/v3/assistant/coordinate/convert?locations=@@arg1,@@arg2&coordsys=gps&output=JSON&key=e63cdf0fcd7cf1d84086241335bd56f5";
        url = url.replaceAll("@@arg1", String.valueOf(longitude));
        url = url.replaceAll("@@arg2", String.valueOf(latitude));
        String result = HttpUtil.get(url);
        //返回结果集
        if(StringUtil.isEmptyOrNull(result)){
           return null;
        }
        JSONObject convertResult = JSON.parseObject(result);
        String info = convertResult.getString("info");
        if(RESULT_SUCCESS.equalsIgnoreCase(info)){
            String locations = convertResult.getString("locations");
            if(!StringUtil.isEmptyOrNull(locations)){
                String[] split = locations.split(",");
                coordinateResp.setLongitude(Double.parseDouble(split[0]));
                coordinateResp.setLatitude(Double.parseDouble(split[1]));
            }
        }
        log.info("cgs2000 to wgs84 ::->longitude:{},latitude:{}", coordinateResp.getLongitude(),coordinateResp.getLatitude());
        return coordinateResp;
    }



    /***
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public static CoordinateResp transformGcj02ToWgs84(double longitude, double latitude) {
        double dLat = transformLat(longitude - 105.0, latitude - 35.0);
        double dLng = transformLng(longitude - 105.0, latitude - 35.0);
        double radLat = latitude / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        double mgLat = latitude + dLat;
        double mgLng = longitude + dLng;
        return new CoordinateResp(longitude * 2 - mgLng,latitude * 2 - mgLat);
    }

    /***
     * 纬度 转换
     * @param lng
     * @param lat
     * @return
     */
    private static double transformLat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat
                + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /***
     * 经度
     * @param lng
     * @param lat
     * @return
     */
    private static double transformLng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }


    /***
     *城运坐标转 Cgs2000
     * @param longitude
     * @param latitude
     * @return
     */
    public static CoordinateResp cityToCgs2000(double longitude, double latitude) {
        String rq = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><SH2WGS84BLH xmlns=\"SHCoordTransM1SW\"><arg0 xmlns=\"\">@@arg1</arg0><arg1 xmlns=\"\">@@arg2</arg1><arg2 xmlns=\"\">0</arg2></SH2WGS84BLH></soap:Body></soap:Envelope>";
        rq = rq.replaceAll("@@arg1", String.valueOf(latitude));
        rq = rq.replaceAll("@@arg2", String.valueOf(longitude));
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "text/xml;charset=utf-8");
        String result = HttpUtil.createPost(CITY_URL2).addHeaders(headers).body(rq).execute(false).body();
        System.out.println(result);
        Matcher m = Pattern.compile("[-+]?\\d+\\.\\d+").matcher(result);
        double y = 0.0;
        double x = 0.0;
        for (int i = 1; i <= 3; i++) {
            m.find();
            if (i == 2) {
                y = Double.parseDouble(m.group());
            }
            if (i == 3) {
                x = Double.parseDouble(m.group());
            }
        }
        y = y * 180 / Math.PI;
        x = x * 180 / Math.PI;
        return new CoordinateResp(x, y);
    }

}
