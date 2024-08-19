package com.street.one.manage.common.core.domain.model;

import com.street.one.manage.common.core.domain.tree.TreeKeyConfig;
import com.street.one.manage.common.utils.TreeUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tjl
 */
@Data
public class MenuTreeKey implements Serializable, TreeKeyConfig {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 部门名称
     */
    private String menuName;

    /**
     * 上级部门ID 根节点此ID为空
     */
    private Integer parentId;

    /**
     * ML001:一级 ML002:二级 ML003:三级
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

    /***
     * 选中状态: 1-选中，0-未选中
     */
    private Boolean selected;


    @Override
    public String getIdKey() {
        return TreeUtils.getName(MenuTreeKey::getMenuId);
    }

    @Override
    public String getParentIdKey() {
        return TreeUtils.getName(MenuTreeKey::getParentId);
    }

    @Override
    public String getNameKey() {
        return TreeUtils.getName(MenuTreeKey::getMenuName);
    }

    @Override
    public String getSelectedKey() {
        return TreeUtils.getName(MenuTreeKey::getSelected);
    }

    @Override
    public String getCodeKey() {
        return "menuCode";
    }

    @Override
    public String getTypeKey() {
       return TreeUtils.getName(MenuTreeKey::getType);
    }

    @Override
    public String getSortKey() {
        return TreeUtils.getName(MenuTreeKey::getSort);
    }

    @Override
    public String getWeightKey() {
        return TreeUtils.getName(MenuTreeKey::getWeightKey);
    }
}