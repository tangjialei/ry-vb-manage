package com.street.one.manage.common.constants;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.constants
 * @ClassName: ScheduleConstants
 * @Author: tjl
 * @Description: 任务调度通用常量
 * @Date: 2024/7/3 14:46
 * @modified modify person name
 * @Version: 1.0
 */
public class ScheduleConstants {


    /***
     * 计划任务类名
     */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /***
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /***
     * 默认
     */
    public static final String MISFIRE_DEFAULT = "0";

    /***
     * 立即触发执行
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /***
     * 触发一次执行
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /***
     * 不触发立即执行
     */
    public static final String MISFIRE_DO_NOTHING = "3";

}
