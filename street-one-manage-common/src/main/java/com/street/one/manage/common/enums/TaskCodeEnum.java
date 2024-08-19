package com.street.one.manage.common.enums;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.enums
 * @ClassName: TaskCodeEnum
 * @Author: tjl
 * @Description: 计划任务执行状态枚举
 * @Date: 2024/7/3 15:37
 * @modified modify person name
 * @Version: 1.0
 */
public enum TaskCodeEnum {

    /***
     * 任务不存在
     */
    TASK_EXISTS,
    /***
     * 没有任务
     */
    NO_TASK_EXISTS,
    /***
     * 任务已启动
     */
    TASK_ALREADY_STARTED,
    /***
     * 未知
     */
    UNKNOWN,
    /***
     * 配置错误
     */
    CONFIG_ERROR,
    /***
     * 任务节点不可用
     */
    TASK_NODE_NOT_AVAILABLE,

    /***
     * 计划任务执行失败
     */
    TASK_EXECUTION_FAILURE
}
