package com.street.one.manage.common.core.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.model
 * @ClassName: ZdReceivingAlarmLog
 * @Author: tjl
 * @Description: 中台推送接处警日志记录对象
 * @Date: 2024/7/4 15:37
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class ZdReceivingAlarmLog implements Serializable {


    private static final long serialVersionUID = 8897562147612424257L;
    /**
     * 任务日志ID
     */
    private Integer id;

    /**
     * 业务类型（0=其它,1=新增,2=修改,3=删除,4=导出,5=导入,6=强退,7=拉取）
     */
    private Integer businessType;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 主机地址
     */
    private String ip;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
