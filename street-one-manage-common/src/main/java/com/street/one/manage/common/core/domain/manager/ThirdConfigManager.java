package com.street.one.manage.common.core.domain.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.street.one.manage.common.core.domain.model.ThirdConfigInfo;
import com.street.one.manage.common.utils.Assert;
import com.street.one.manage.common.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.manager
 * @ClassName: ThirdConfigManager
 * @Author: tjl
 * @Description: 第三方配置管理类
 * @Date: 2024/6/14 17:56
 * @modified modify person name
 * @Version: 1.0
 */
@Component
public class ThirdConfigManager {

    /***
     * 配置集合，根据类型区分
     */
    private static Map<String, ThirdConfigInfo> thirdConfigInfoMap = new ConcurrentHashMap<>();

    /***
     * 初始化第三方配置
     * @param thirdConfig
     */
    public  void addThirdConfig(ThirdConfigInfo thirdConfig){
        thirdConfigInfoMap.putIfAbsent(thirdConfig.getThirdConfigCode(),thirdConfig);
    }

    /***
     * 获取配置
     * @param configCode
     * @return
     */
    public static ThirdConfigInfo getThirdConfig(String configCode){
        return thirdConfigInfoMap.get(configCode);
    }


    /***
     * 获取配置前缀
     * @param configCode
     * @return
     */
    public static String getIpPrefix(String configCode){
        ThirdConfigInfo configInfo = thirdConfigInfoMap.get(configCode);
        Assert.notNull(configInfo,String.format("第三方配置[%s]不存在",configCode));
        return configInfo.getThirdIpPrefix();
    }

    /***
     * 获取第三方配置参数
     * @param configCode
     * @return
     */
    public static JSONObject getThirdConfigParameter(String configCode){
        ThirdConfigInfo configInfo = thirdConfigInfoMap.get(configCode);
        Assert.notNull(configInfo,String.format("第三方配置[%s]不存在",configCode));
        if(StringUtil.isEmptyOrNull(configInfo.getThirdReqParams())){
           return null;
        }
        return JSON.parseObject(configInfo.getThirdReqParams());
    }

    /***
     * 删除配置
     * @param configCode
     */
    public  void  deleteThirdConfig(String configCode){
        thirdConfigInfoMap.remove(configCode);
    }

    /***
     * 重置缓存
     */
    public  void reset(){
        thirdConfigInfoMap.clear();
    }

}
