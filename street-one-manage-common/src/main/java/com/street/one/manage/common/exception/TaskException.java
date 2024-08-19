package com.street.one.manage.common.exception;

import com.street.one.manage.common.enums.TaskCodeEnum;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.exception
 * @ClassName: TaskException
 * @Author: tjl
 * @Description: 计划任务自定义异常
 * @Date: 2024/7/3 15:33
 * @modified modify person name
 * @Version: 1.0
 */
public class TaskException extends Exception {
    private static final long serialVersionUID = 6380323927633678222L;

    /***
     * 自定义的计划任务错误异常
     */
    private TaskCodeEnum code;

    public TaskException(String msg, TaskCodeEnum code) {
        this(msg, code, null);
    }

    public TaskException(String msg, TaskCodeEnum code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public TaskCodeEnum getCode() {
        return code;
    }

}
