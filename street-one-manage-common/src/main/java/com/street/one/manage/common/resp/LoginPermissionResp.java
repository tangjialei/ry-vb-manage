package com.street.one.manage.common.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginPermissionResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 上级ID
     */
    private Integer parentId;

    /**
     * 类型
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;
}
