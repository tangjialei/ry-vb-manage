package com.street.one.manage.common.core.domain.cache;

import cn.hutool.cache.impl.TimedCache;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.cache
 * @ClassName: DateCenterTokenCache
 * @Author: tjl
 * @Description: 数据中枢-token管理
 * @Date: 2024/3/26 14:35
 * @modified modify person name
 * @Version: 1.0
 */
public class DataServiceTokenCache {
    /**
     * JWT过期时间  1分钟//60000  默认12小时
     */
    public static final Long EXPIRED_TIME = 43200000L;

    /***
     *定时删除 30秒过期
     */
    private static TimedCache<String, Object> TIMED_CACHE = new TimedCache<>(EXPIRED_TIME);


    /***
     * 获取缓存对象
     * @return
     */
    public static TimedCache<String, Object> getCache() {
        return TIMED_CACHE;
    }

    static {
        //每10秒检查一次
        TIMED_CACHE.schedulePrune(10);
    }


    /***
     * 缓存新增key-values 过期时间
     * @param key key
     * @param resp values
     * @param timeout 过期时间
     */
    public static void putValue(String key,Object resp,Long timeout){
        TIMED_CACHE.put(key,resp,timeout);
    }

    /**
     * 每次重新get一次缓存，均会重新刷新消逝时间
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        return key == null ? null : TIMED_CACHE.get(key,false);
    }

    /***
     * 缓存 锁
     * @param key key
     * @param resp values
     * @param timeout 过期时间
     */
    public static synchronized Boolean putLocalValue(String key,Object resp,Long timeout){
        if(TokenCache.getCache().containsKey(key)){
            return false;
        }
        TIMED_CACHE.put(key,resp,timeout);
        return true;
    }

    /**
     * 删除缓存key，支持多个
     * @param key
     * @return
     */
    public static void del(String ... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                TIMED_CACHE.remove(key[0]);
            } else {
                for (String s : key) {
                    TIMED_CACHE.remove(s);
                }
            }
        }
    }
}
