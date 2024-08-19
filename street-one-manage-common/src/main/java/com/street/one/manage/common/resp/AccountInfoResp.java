package com.street.one.manage.common.resp;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountInfoResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer accountId;

    /**
     * 用户代码
     */
    private String accountCode;

    /**
     * 登录名称
     */
    private String accountName;

    /**
     * 用户姓名
     */
    private String realName;


    /**
     * 所在部门ID
     */
    private Integer departmentId;

    /**
     * 所在部门名称
     */
    private String departmentName;

    /**
     * 所在部门标签
     */
    private String departmentLabel;

    /**
     * 所属角色ID
     */
    private String roleIds;

    /**
     * 所属角色名称
     */
    private String roleNames;

    /**
     * 菜单及按钮权限
     */
    private List<LoginPermissionResp> permission;

    /**
     * token
     */
    private String token;

    /**
     * 有效期 时间戳
     */
    private long tokenExp;
}
