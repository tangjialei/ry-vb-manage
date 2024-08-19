package com.street.one.manage.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: ExcelTemplateReflection
 * @Author: tjl
 * @Description:导入模版的文件名映射
 * @Date: 2024/7/2 16:22
 * @modified modify person name
 * @Version: 1.0
 */
public class ExcelTemplateReflection {

    private static Map<String, String> tempMap;
    static {
        tempMap = new HashMap<>();
        tempMap.put("BXKM","必训科目");
        tempMap.put("PXKM","普训科目");
        tempMap.put("DXKJ","地下空间");
        tempMap.put("DZ","地震");
        tempMap.put("FH","防化");
        tempMap.put("GJSL","攻坚实力");
        tempMap.put("JSY","驾驶员");
        tempMap.put("SHJZ","社会救助");
        tempMap.put("SS","绳索");
        tempMap.put("SY","水域");
        tempMap.put("TXDD","通信调度");
        tempMap.put("ZJRY","侦检人员");
    }

    /***
     * 根据code获取模板名称
     * @param code
     * @return
     */
    public static String getTempFileName(String code) {
        return tempMap.get(code);
    }

}
