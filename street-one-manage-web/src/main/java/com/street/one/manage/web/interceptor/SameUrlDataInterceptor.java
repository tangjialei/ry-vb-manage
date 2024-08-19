package com.street.one.manage.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.annotation.RepeatSubmit;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.cache.LocalHostCache;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.web.config.log.RequestWrapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.interceptor
 * @ClassName: SameUrlDataInterceptor
 * @Author: tjl
 * @Description: 判断请求url和数据是否和上一次相同，如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 * @Date: 2024/6/14 11:40
 * @modified modify person name
 * @Version: 1.0
 */
@Component
public class SameUrlDataInterceptor extends AbstractInterceptor {

    /***
     *重复参数
     */
    public final String REPEAT_PARAMS = "repeatParams";

    /***
     * 重复时间
     */
    public final String REPEAT_TIME = "repeatTime";


    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) throws IOException {
        String requestParam = "";
        if(request instanceof RequestWrapper){
            RequestWrapper requestWrapper = new RequestWrapper(request);
            requestParam = requestWrapper.getBody();
        }
        // body参数为空，获取Parameter的数据
        if (StringUtil.isEmptyOrNull(requestParam)){
            requestParam = JSON.toJSONString(request.getParameterMap());
        }
        Map<String, Object> nowDataMap = Maps.newHashMap();
        nowDataMap.put(REPEAT_PARAMS, requestParam);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();
        // 唯一值（没有消息头则使用请求地址）
        String submitKey = StringUtils.trimToEmpty(request.getHeader(CommonConstants.TOKEN_HEADER_PREFIX));
        // 唯一标识（指定key + url + 消息头）
        String cacheRepeatKey = CommonConstants.REPEAT_SUBMIT_KEY + url + submitKey;
        Object value = LocalHostCache.getValue(cacheRepeatKey);
        if(null != value){
            Map<String, Object> sessionMap = (Map<String, Object>) value;
            if (sessionMap.containsKey(url)){
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                return compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, annotation.interval());
            }
        }
        Map<String, Object> cacheMap = Maps.newHashMap();
        cacheMap.put(url, nowDataMap);
        LocalHostCache.putValue(cacheRepeatKey,cacheMap, (long) annotation.interval());
        return false;
    }


    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }


    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < interval;
    }
}
