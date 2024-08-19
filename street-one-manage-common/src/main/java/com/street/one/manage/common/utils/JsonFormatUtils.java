package com.street.one.manage.common.utils;

/**
 * Json字符串格式化
 *
 * @author tjl
 */
public class JsonFormatUtils {

    /** 换行符 */
    public final static String NEWLINE = "\n";
    /** 开头缩进 */
    public final static String INDENT = "    ";

    /**
     * 字符串形式展开
     *
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuilder jsonForMatStr = new StringBuilder();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c).append(NEWLINE);
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c).append(NEWLINE);
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append(NEWLINE);
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    /**
     * 区分层级
     *
     * @param level
     * @return
     */
    private static String getLevelStr(int level) {
        StringBuilder levelStr = new StringBuilder();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append(INDENT);
        }
        return levelStr.toString();
    }

}
