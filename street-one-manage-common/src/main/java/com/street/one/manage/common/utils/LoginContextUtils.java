package com.street.one.manage.common.utils;

import com.street.one.manage.common.resp.AccountInfoResp;

/**
 * @ProjectName: xhxf-web-portal
 * @Package: com.xhxf.web.portal.manage.common.utils
 * @ClassName: LoginContextUtils
 * @Author: tjl
 * @Description: 获取登录上下文
 * @Date: 2024/4/19 14:24
 * @modified modify person name
 * @Version: 1.0
 */
public class LoginContextUtils {

    /***
     * ThreadLocal级别的AccountInfoResp，每个线程都会有独立的
     */
    private static ThreadLocal<AccountInfoResp> accountInfoThreadLocal = new ThreadLocal<>();

    public static void setAccountInfoResp(AccountInfoResp accountInfoResp) {
        LoginContextUtils.accountInfoThreadLocal.set(accountInfoResp);
    }



    /***
     * 清除所有ThreadLocal信息
     */
    public static void remove() {
        accountInfoThreadLocal.remove();
    }


    /***
     * 获取当前登录用户信息
     * @return
     */
    public static AccountInfoResp getLoginContext() {
       return accountInfoThreadLocal.get();
    }

}
