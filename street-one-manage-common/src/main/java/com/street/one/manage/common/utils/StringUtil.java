package com.street.one.manage.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {

    public static String EMPTY = "";

    public static boolean isEmptyOrNull(String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    /**
     * @param str
     * @return
     */
    public static String toFirstCharUpper(String str) {
        if (StringUtil.isEmptyOrNull(str)) {
            return StringUtil.EMPTY;
        }
        char c = str.charAt(0);
        if (Character.isUpperCase(c)) {
            return str;
        }
        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(0, Character.toUpperCase(c));
        return sb.toString();
    }

    public static String format(String value, Object... paras) {
        return MessageFormat.format(value, paras);
    }

    /**
     * 判断字符串是否全是数字
     *
     * @param str
     * @return 返回true，否则false
     */
    public static boolean isNum(String str) {
        return !isEmptyOrNull(str) && str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public static String getKey(Object... args) {
        return getKeyBySplit("_", args);
    }

    public static String getKeyBySplit(String split, Object... args) {
        StringBuilder sb = new StringBuilder();

        for (Object arg : args) {
            sb.append(arg);
            sb.append(split);
        }

        int length = sb.length();
        if (length > 0) {
            sb.delete(length - split.length(), length);
        }

        return sb.toString();
    }

    /**
     * 根据前缀过滤source
     *
     * @param prefix    前綴
     * @param source    过滤源
     * @param cutPrefix 返回值是否需要去掉前缀
     * @return
     */
    public static List<String> filterByPrefix(String prefix, List<String> source, boolean cutPrefix) {
        source = source.stream().filter(v -> v != null).map(v -> v.trim()).collect(Collectors.toList());

        if (prefix == null || prefix.equals("")) {
            return new ArrayList<>(source);
        } else {
            Stream<String> stream = source.stream().filter(m -> m.startsWith(prefix));

            if (cutPrefix) {
                stream = stream.map(m -> m.substring(prefix.length()));
            }

            return stream.collect(Collectors.toList());

        }
    }

    /***
     * 删除字符串中指定的字符串
     * @param originalStr 原字符串
     * @param removeStr 需要删除的字符传
     * @param isSingleRemove true 删除一个 false 全量删除
     * @param isFirst 在 isSingleRemove为true的情况下 true删除首步骤 false 删除尾部
     * @return
     */
    public static String removeMatchStr(String originalStr, String removeStr, Boolean isSingleRemove, Boolean isFirst) {
        if (!originalStr.contains(removeStr)) {
            return originalStr;
        }
        if (StringUtil.isEmptyOrNull(originalStr) || StringUtil.isEmptyOrNull(removeStr)) {
            return originalStr;
        }
        if (!isSingleRemove) {
            originalStr = originalStr.replaceAll(removeStr, "");
            return originalStr;
        }
        if (isFirst) {
            originalStr = originalStr.replaceFirst(removeStr, "");
            return originalStr;
        }
        originalStr = originalStr.substring(0, originalStr.length() - removeStr.length());
        return originalStr;
    }

    public static String getSubString(String str, int length) {
        if (isEmptyOrNull(str)) {
            return str;
        }
        try {
            int i;
            int n;
            byte[] bytes = str.getBytes("Unicode");      //使用Unicode字符集将字符串编码成byte序列
            i = 2;      //bytes的前两个字节是标志位，bytes[0] = -2, bytes[1] = -1, 故从第二位开始
            n = 0;
            for (; i < bytes.length && n < length; i++) {
                if (i % 2 == 1) {
                    n++;
                } else {
                    if (bytes[i] != 0) {
                        n++;
                    }
                }
            }
            //去掉半个汉字
            if (i % 2 == 1) {
                if (bytes[i - 1] != 0) {
                    i = i - 1;
                } else {
                    i = i + 1;
                }
            }
            return new String(bytes, 0, i, "Unicode");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 截取字符串
     *
     * @param str 字符串
     * @param start 开始
     * @param end 结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result;
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

    /**
     * 字符串转Integer数组
     * @param str
     * @return
     */
    public static Integer[] stringToArrays(String str) {
        Integer[] arr = null;
        if(!StringUtil.isEmptyOrNull(str)) {
            String[] arrStr = str.split(",");
            arr = new Integer[arrStr.length];
            int i = 0;
            for (String s : arrStr) {
                arr[i++] = Integer.parseInt(s);
            }
        }
        return arr;
    }

    /**
     * 数组转字符串
     * @param arr
     * @return
     */
    public static String arrsysToString(Integer[] arr) {
        String str = Arrays.toString(arr);
        str = str.replaceAll("\\[","").replaceAll("]","").replaceAll(" ","");
        return str;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }



    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }



    public static void main(String[] args) throws Exception {
        System.out.println(autoGenericCode("1000", 8));
//        System.out.println(removeMatchStr("33666554", "23", false, false));
//        System.out.println(removeMatchStr("23AAA111223", "23", false, false));
//        System.out.println(removeMatchStr("23AAA111223", "23", true, true));
//        System.out.println(removeMatchStr("23AAA111223", "23", true, false));

    }
}
