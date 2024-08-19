package com.street.one.manage.common.utils;

import java.util.Random;
import java.util.StringJoiner;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: SequenceUtils
 * @Author: tjl
 * @Description: 序列工具类
 * @Date: 2023/6/21 17:04
 * @modified modify person name
 * @Version: 1.0
 */
public final class SequenceUtils {
    private SequenceUtils(){}

    /**
     * 生成20位序列：当前时间纳秒值 + 随机数
     * @return
     */
    public static String generatorSeq() {
        long nano = System.nanoTime();
        String nanoStr = String.valueOf(nano);
        int length = 20 - nanoStr.length();
        String random = genRandom(length);
        return nanoStr.concat(random);
    }

    private static String genRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i <= length - 1; i++) {
            //生成1~9之间的随机数
            sb.append(r.nextInt(9) + 1);
        }
        return sb.toString();
    }

    /**
     * key拼接 ：规则 -> groupCode_collectionName
     * @param groupCode
     * @param key
     */
    public static String buildKey(String groupCode, String key) {
        StringJoiner joiner = new StringJoiner("_");
        joiner.add(groupCode).add(key);
        return joiner.toString();
    }
}
