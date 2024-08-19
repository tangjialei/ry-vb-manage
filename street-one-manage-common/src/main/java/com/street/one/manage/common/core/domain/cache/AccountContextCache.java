package com.street.one.manage.common.core.domain.cache;

import cn.hutool.cache.impl.TimedCache;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.utils.SequenceUtils;

/**
 * @ProjectName: xhxf-web-portal-manage
 * @Package: com.xhxf.web.portal.manage.common.utils
 * @ClassName: AccountContext
 * @Author: FC
 * @Description: 账号上下文缓存，相当于redis
 * @Date: 2024/3/25 17:34
 * @modified modify person name
 * @Version: 1.0
 */
public class AccountContextCache {

    /**
     * JWT过期时间 30分钟//7200000L 1分钟//60000
     */
    public static final Long EXPIRED_TIME = 1800000L;

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
        //生成key
        String realKey = SequenceUtils.buildKey(CommonConstants.CACHE_PREFIX, key);
        TIMED_CACHE.put(realKey,resp,timeout);
    }

    /**
     * 每次重新get一次缓存，不会重新刷新消逝时间
     * @param key
     * @param isUpdateLastAccess  true-重新刷新消逝时间，false-不会重新刷新消逝时间
     * @return
     */
    public static Object getValue(String groupCode,String key,Boolean isUpdateLastAccess) {
        String realKey = SequenceUtils.buildKey(groupCode, key);
        return realKey == null ? null : TIMED_CACHE.get(realKey,isUpdateLastAccess);
    }

    /***
     * 缓存 锁
     * @param key key
     * @param resp values
     * @param timeout 过期时间
     */
    public static synchronized Boolean putLocalValue(String key,Object resp,Long timeout){
        if(AccountContextCache.getCache().containsKey(key)){
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
