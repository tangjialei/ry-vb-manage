package com.street.one.manage.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: ExceptionUtil
 * @Author: tjl
 * @Description: 错误信息处理类
 * @Date: 2024/7/3 14:56
 * @modified modify person name
 * @Version: 1.0
 */
public class ExceptionUtil {


    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

}
