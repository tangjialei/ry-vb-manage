package com.street.one.manage.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class HttpUtils {

    /***
     * get请求
     * @param url 请求路径
     * @param headers  请求头参数
     * @param reqParams 请求url参数
     * @return
     */
    public static BaseResponse sentGet(String url, Map<String,String> headers, Map<String,Object> reqParams){
        if(StringUtil.isEmptyOrNull(url)){
            throw new BusinessException(BaseResponseCodeEnum.E400.getMsg());
        }
        HttpResponse execute = null;
        try{
            //创建get请求
            HttpRequest get = HttpUtil.createGet(url);
            if(!CollectionUtils.isEmpty(headers)){
                get.addHeaders(headers);
            }
            if(!CollectionUtils.isEmpty(reqParams)){
                get.form(reqParams);
            }
            //执行get请求
            execute = get.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(null == execute || CommonConstants.HTTP_CODE != execute.getStatus()){
            throw new BusinessException("get请求第三方接口失败！");
        }
        return getBaseResponse(execute);
    }

    /***
     * post请求
     * @param url 请求url
     * @param headers
     * @param body
     * @return
     */
    public static BaseResponse sentPost(String url,Map<String,String> headers,String body){
        if(StringUtil.isEmptyOrNull(url)){
            throw new BusinessException(BaseResponseCodeEnum.E400.getMsg());
        }
        HttpResponse execute = null;
        try{
            HttpRequest post = HttpUtil.createPost(url);
            if(!CollectionUtils.isEmpty(headers)){
                post.addHeaders(headers);
            }
            if(!StringUtil.isEmptyOrNull(body)){
                post.body(body);
            }
            execute = post.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (null == execute || CommonConstants.HTTP_CODE != execute.getStatus()) {
            throw new BusinessException("post请求第三方接口失败！");
        }
        return getBaseResponse(execute);
    }

    private static BaseResponse getBaseResponse(HttpResponse httpResponse) {
        String body = httpResponse.body();
        JSONObject result = JSON.parseObject(body);
        if(result.get("code").equals(CommonConstants.HTTP_RESPONSE_CODE)) {
            return BaseResponseUtil.success(result.get("data"));
        } else {
            return BaseResponseUtil.fail(String.valueOf(result.get("message")));
        }
    }

    public static JSONArray sentPostArray(String url, Map<String,String> headers, String body){
        if(StringUtil.isEmptyOrNull(url)){
            throw new BusinessException(BaseResponseCodeEnum.E400.getMsg());
        }
        HttpResponse execute = null;
        try{
            HttpRequest post = HttpUtil.createPost(url);
            if(!CollectionUtils.isEmpty(headers)){
                post.addHeaders(headers);
            }
            if(!StringUtil.isEmptyOrNull(body)){
                post.body(body);
            }
            execute = post.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (null == execute || CommonConstants.HTTP_CODE != execute.getStatus()) {
            throw new BusinessException("post请求第三方接口失败！");
        }
        return getJSONArray(execute);
    }

    private static JSONArray getJSONArray(HttpResponse httpResponse) {
        String body = httpResponse.body();
        JSONObject result = JSON.parseObject(body);
        if(result.get("code").equals(CommonConstants.HTTP_RESPONSE_CODE)) {
            return (JSONArray) result.get("data");
        } else {
            return null;
        }
    }

    /**
     * 获取信息网Token
     */
//    public static String getXHXFWebPortalToken(String url) {
//        // 系统缓存获取数据中台Token
//        String token = String.valueOf(LocalHostCache.getValue("xhxfWebPortalToken"));
//        if(StringUtil.isEmptyOrNull(token)) {
//            putXHXFWebPortalToken();
//            token = String.valueOf(LocalHostCache.getValue("xhxfWebPortalToken"));
//        } else {
//            // 校验Token是否有效
//            String berifyUrl = "http://" + xhxf_web_portal[0] + "/api/platform/is_verify?token=" + token;
//            HttpResponse httpResponseVerify = HttpUtil.createGet(berifyUrl).execute();
//            String bodyVerify = httpResponseVerify.body();
//            JSONObject resultVerify = JSON.parseObject(bodyVerify);
//            if (resultVerify.get("code").equals("0000") && !resultVerify.getBoolean("data")) {
//                putXHXFWebPortalToken();
//                token = String.valueOf(LocalHostCache.getValue("xhxfWebPortalToken"));
//            }
//        }
//        return token;
//    }

    /**
     * 调取数据中台接口获取Token
     */
//    private static void putXHXFWebPortalToken() {
//        String tokenUrl = "http://" + xhxf_web_portal[0] +  "/api/platform/login";
//        Map<String, String> headerMap = Maps.newHashMap();
//        JSONObject paramObject = new JSONObject();
//        paramObject.put("userCode", xhxf_web_portal[1]);
//        paramObject.put("password", xhxf_web_portal[2]);
//        BaseResponse response = sentPost(tokenUrl, headerMap, paramObject.toJSONString());
//        JSONObject data = (JSONObject) response.getData();
//        JSONObject tokenInfoResp = data.getJSONObject("tokenInfoResp");
//        String token = String.valueOf(tokenInfoResp.get("token"));
//        if(!StringUtil.isEmptyOrNull(token)) {
//            LocalHostCache.putValue("xhxfWebPortalToken",token, CommonConstants.EXPIRED_TIME);
//        }
//    }

    public static void main(String[] args) {
//        String str = HttpUtils.sentGet("http://10.204.218.124/api/access/platform_token", null, null);
//        System.out.println(str);
    }
}
