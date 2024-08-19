package com.street.one.manage.common.utils;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: LogUtils
 * @Author: tjl
 * @Description: 处理并记录日志文件
 * @Date: 2024/6/24 11:01
 * @modified modify person name
 * @Version: 1.0
 */
public class LogUtils {

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
