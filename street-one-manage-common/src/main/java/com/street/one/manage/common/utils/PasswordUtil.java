package com.street.one.manage.common.utils;


import com.street.one.manage.common.constants.CommonConstants;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: PasswordUtil
 * @Author: tjl
 * @Description: 密码工具类
 * @Date: 2023/6/25 10:35
 * @modified modify person name
 * @Version: 1.0
 */
public class PasswordUtil {

    /**
     * 密码必须包含大写、小写、数字和特殊字符，且长度是8位以上
     */
    private static final String PWD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()=_+;':,.?]).{8,}$";


    /**
     * 系统用户密码加密
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return SecurityUtils.getEncryptMD5HexEncoding(password);
    }

    /**
     * 获取系统用户初始密码
     *
     * @return
     */
    public static String getDefaultPassword() {
        return encryptPassword(CommonConstants.SYS_USER_DEFAULT_PW);
    }

    /**
     * 密码复杂度校验
     * @param password 密码
     * @return 校验密码强度是否合格 true/false
     */
    public static boolean isCheckPassword(String password) {
        if (StringUtil.isEmptyOrNull(password)) {
            return false;
        }
        return password.matches(PWD_REGEX);
    }


    public static void main(String[] args) {
        System.out.println(getDefaultPassword());
    }


}
