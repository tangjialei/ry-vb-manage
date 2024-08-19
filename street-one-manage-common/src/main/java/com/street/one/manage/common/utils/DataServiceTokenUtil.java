package com.street.one.manage.common.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.constants.ThirdConfigConstants;
import com.street.one.manage.common.core.domain.cache.DataServiceTokenCache;
import com.street.one.manage.common.core.domain.model.ThirdConfigInfo;
import com.street.one.manage.common.core.domain.manager.ThirdConfigManager;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.service.utils
 * @ClassName: DateCenterTokenUtil
 * @Author: tjl
 * @Description: 数据中枢 -token工具类
 * @Date: 2024/3/26 14:46
 * @modified modify person name
 * @Version: 1.0
 */
@Component
public class DataServiceTokenUtil {

//    private static IBasisThirdApiLogService basisThirdApiLogService = SpringApplicationContext.getBean(IBasisThirdApiLogService.class);

    /****
     * 获取token
     * @return
     */
    public static BizBaseResult getToken() {
        //获取配置信息
        ThirdConfigInfo configInfo = ThirdConfigManager.getThirdConfig(ThirdConfigConstants.XH_DATA_SERVICE);
        //解析参数
        String params = configInfo.getThirdReqParams();
        if(StringUtil.isEmptyOrNull(params)){
            return BizBaseResult.fail("配置请求认证参数不能为空!");
        }
        //数据中枢配置参数
        JSONObject dataParams = JSON.parseObject(params);
        //请求地址
        String url = configInfo.getThirdIpPrefix() + "/restcloud/api/v2/token/create";
        //用户id
        String userId = dataParams.getString("userId");
        //应用id
        String appId = dataParams.getString("appId");
        //缓存key
        String key = configInfo.getThirdConfigCode() + "::" + userId + "::" + appId;
        //获取缓存token
        String tokenCache = (String) DataServiceTokenCache.getValue(key);
        //先判断token是否存在
        if(!StringUtil.isEmptyOrNull(tokenCache)){
            return BizBaseResult.success("success",tokenCache);
        }
        //token为空 则去请求
        JSONObject body = new JSONObject();
        body.put("userId", userId);
        body.put("appId", appId);
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Content-Type", "application/json; charset=utf-8");
        String response ="";
        try{
            HttpResponse execute = HttpUtil.createPost(url).addHeaders(headers).body(body.toJSONString()).execute();
            if(CommonConstants.HTTP_CODE != execute.getStatus()){
                return BizBaseResult.fail("请求城运获取token接口,请求失败了！");
            }
            //记录调用成功日志
//            basisThirdApiLogService.writeApiLog("getToken",url,JSON.toJSONString(body),response,true);
            response = execute.body();
            JSONObject resultData = JSON.parseObject(response);
            int code = resultData.getIntValue("code");
            if(0 != code){
                String errorMsg = resultData.getString("msg");
                return BizBaseResult.fail(String.format("请求城运获取token接口返回失败,失败原因:%s",errorMsg));
            }
            JSONObject data = resultData.getJSONObject("data");
            //获取token
            String token = data.getString("YYZX-APP-TOKEN");
            //加入缓存
            //对应的缓存的超时时间 这里减少5s 避免刚好再两个小时内请求
            long timeout = CommonConstants.EXPIRED_TIME - 5000;
            DataServiceTokenCache.putValue(key,token,timeout);
            return BizBaseResult.success("success",token);
        }catch (Exception e){
            e.printStackTrace();
            //记录调用失败日志
//            basisThirdApiLogService.writeApiLog("getToken",url,JSON.toJSONString(body),response,false);
            return BizBaseResult.fail(String.format("获取token失败,失败原因:%s",e.getMessage()));
        }
    }
}
