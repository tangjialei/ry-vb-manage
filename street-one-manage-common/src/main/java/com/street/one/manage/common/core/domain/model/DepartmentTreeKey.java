package com.street.one.manage.common.core.domain.model;

import com.street.one.manage.common.core.domain.tree.TreeKeyConfig;
import com.street.one.manage.common.utils.TreeUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tjl
 */
@Data
public class DepartmentTreeKey implements Serializable, TreeKeyConfig {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 部门代码
     */
    private String departmentCode;

    /**
     * 部门名称
     */
    private String departmentName;

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
        return TreeUtils.getName(DepartmentTreeKey::getDepartmentId);
    }

    @Override
    public String getParentIdKey() {
        return TreeUtils.getName(DepartmentTreeKey::getParentId);
    }

    @Override
    public String getNameKey() {
        return TreeUtils.getName(DepartmentTreeKey::getDepartmentName);
    }

    @Override
    public String getSelectedKey() {
        return TreeUtils.getName(DepartmentTreeKey::getSelected);
    }

    @Override
    public String getCodeKey() {
        return TreeUtils.getName(DepartmentTreeKey::getDepartmentCode);
    }

    @Override
    public String getTypeKey() {
        return TreeUtils.getName(DepartmentTreeKey::getType);
    }

    @Override
    public String getSortKey() {
        return TreeUtils.getName(DepartmentTreeKey::getSort);
    }

    @Override
    public String getWeightKey() {
        return TreeUtils.getName(DepartmentTreeKey::getWeightKey);
    }
}
