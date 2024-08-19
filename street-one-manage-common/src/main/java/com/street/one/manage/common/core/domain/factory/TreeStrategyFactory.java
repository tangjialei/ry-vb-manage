package com.street.one.manage.common.core.domain.factory;

import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.model.DepartmentTreeKey;
import com.street.one.manage.common.core.domain.model.MenuTreeKey;
import com.street.one.manage.common.core.domain.tree.TreeKeyConfig;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.factory
 * @ClassName: TreeStrategyFactory
 * @Author: tjl
 * @Description: 树形菜单工厂类
 * @Date: 2024/5/24 17:58
 * @modified modify person name
 * @Version: 1.0
 */
public class TreeStrategyFactory {

    public static TreeKeyConfig getTreeKeyConfig(String treeType) {
        if(treeType.equals(CommonConstants.TREE_TYPE_01)){
            return new MenuTreeKey();
        }else if(treeType.equals(CommonConstants.TREE_TYPE_02)){
            return new DepartmentTreeKey();
        }
        return null;
    }

}
