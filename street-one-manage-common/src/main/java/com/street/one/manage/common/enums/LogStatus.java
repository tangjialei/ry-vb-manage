package com.street.one.manage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.enums
 * @ClassName: JobLogStatus
 * @Author: tjl
 * @Description: 计划任务日志执行状态枚举
 * @Date: 2024/7/3 14:31
 * @modified modify person name
 * @Version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum LogStatus {


    /***
     * 初始日志
     */
    INIT("init"),

    /**
     * 成功
     */
    SUCCESS("success"),

    /**
     * 失败
     */
    ERROR("error");

    /***
     * 计划任务日志类型
     */
    private String type;

}
