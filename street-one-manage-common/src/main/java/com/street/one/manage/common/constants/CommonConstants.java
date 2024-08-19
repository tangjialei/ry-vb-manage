package com.street.one.manage.common.constants;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.constants
 * @ClassName: LocalCacheConstants
 * @Author: tjl
 * @Description: 本地缓存常量池
 * @Date: 2023/6/21 17:39
 * @modified modify person name
 * @Version: 1.0
 */
public final class CommonConstants {

    /***
     * 请求方法类型  GET
     */
    public static final String  METHOD_TYPE = "GET";

    /***
     * 请求方法类型  POST
     */
    public static final String POST_METHOD = "POST";

    /***
     * 字符集
     */
    public static final String CHARSET = "UTF-8";

    /**
     * JWT过期时间 24小时/86400000L 1分钟//60000
     */
    public static final Long TOKEN_EXPIRED_TIME = 86400000L;

    /**
     * JWT过期时间 2小时//7200000L 1分钟//60000
     */
    public static final Long EXPIRED_TIME = 7200000L;

    /**
     * JWT过期时间 12小时/43200000L
     */
    public static final Long TOKEN_EXPIRED_TIME_TWELVE = 43200000L;

    /**
     * 系统用户默认密码
     */
    public static final String SYS_USER_DEFAULT_PW = "123456";

    /**
     * 令牌自定义标识
     */
    public static final String TOKEN_HEADER_PREFIX = "token";

    /**
     * 缓存 key前缀
     */
    public static final String CACHE_PREFIX = "crm";


    /**
     * 系统判断删除
     */
    public static final Integer SYS_ISDEL_FALSE = 0;
    public static final Integer SYS_ISDEL_TRUE = 1;

    /**
     * 树形结构-菜单
     */
    public static final String TREE_TYPE_01 = "T01";
    /**
     * 树形结构-部门
     */
    public static final String TREE_TYPE_02 = "T02";

    /***
     * http 返回状态
     */
    public static final int HTTP_CODE = 200;

    /***
     * http 返回code
     */
    public static final String HTTP_RESPONSE_CODE = "0000";

    /**
     * 层级关系
     */
    public static final String LEVEL1 = "ML001";
    public static final String LEVEL2 = "ML002";
    public static final String LEVEL3 = "ML003";

    /**
     * 部门标签
     */
    public static final String LABEL1 = "DL01"; // 科室
    public static final String LABEL2 = "DL02"; // 队站
    public static final String LABEL3 = "DL03"; // 社区微站
    public static final String LABEL4 = "DL04"; // 单位微站
    public static final String LABEL5 = "DL05"; // 小型站

    /**
     * 积分训练科目等级
     */
    public static final String SUBJECT_LEVEL1 = "SL001";

    /***
     * 数据源是否切换标识
     * sd-默认
     */
    public static final String SYS_TE_MDB = "sd";

    /***
     * 验证码图片，math-算数，random-随机数
     */
    public static final String CODE_GENERATOR_MATH_TYPE = "math";

    public static final String CODE_GENERATOR_MATH_RANDOM = "random";

    /***
     * 图形化缓存前缀
     */
    public static final String CAPTCHA_CODE_PREFIX = "captcha_";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";


    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "SUCCESS";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "ERROR";

    /**
     * 退出登录
     */
    public static final String LOGOUT = "LOGOUT";

    /***
     * 中台警情推送标记
     */
    public static final String SOCKET_MESSAGE_TYPE_03 = "SM03";

    /***
     * 默认的文件格式
     */
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf" };

    /***
     * 获取图片的所有后缀
     * @return
     */
    public static List<String> getFileType(){
        List<String> type = Lists.newArrayList();
        type.add(".jpg");
        type.add(".gif");
        type.add(".png");
        type.add(".bmp");
        type.add(".webp");
        type.add(".tiff");
        type.add(".jpeg");
        type.add(".ai");
        type.add(".cdr");
        type.add(".eps");
        return type;
    }

    /**
     * 队站仓库类型
     */
    public static final String WAREHOUSE_1 = "WT01"; // 在库
    public static final String WAREHOUSE_2 = "WT02"; // 维修

    public static Integer getDutyFireEngineStatus(String status) {
        Map<String, Integer> map = new HashMap<>();
        map.put("执勤", 1);
        map.put("训练", 2);
        map.put("调研", 3);
        map.put("故障", 4);
        map.put("修理", 5);
        map.put("备勤", 6);
        map.put("驻防", 7);
        map.put("联动", 8);
        map.put("跨训", 9);
        map.put("加油", 10);
        map.put("保养", 11);
        map.put("演习", 12);
        map.put("公务", 13);
        map.put("增援", 14);
        return map.get(status);
    }

    /***
     * 启用/停用状枚举
     */
    public enum Status
    {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        private Status(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    /***
     * 数据中枢新接口返回的成功状态
     */
    public static final String DATA_SERVICE_RESULT_STATUS = "00000";
}
