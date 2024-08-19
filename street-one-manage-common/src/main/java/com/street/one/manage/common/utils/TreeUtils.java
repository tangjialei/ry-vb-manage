package com.street.one.manage.common.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.street.one.manage.common.core.domain.function.SerializableFunction;
import com.street.one.manage.common.core.domain.tree.TreeKeyConfig;
import com.street.one.manage.common.core.domain.tree.TreeNodeRequest;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TreeUtils {

    /***
     * 获取树配置属性相关配置
     * @return
     */
    public static TreeNodeConfig getTreeNodeConfig(Integer deep,TreeKeyConfig treeKeyConfig){
        //配置前端菜单
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey(treeKeyConfig.getIdKey());
        //父id
        config.setParentIdKey(treeKeyConfig.getParentIdKey());
        //分类名称
        config.setNameKey(treeKeyConfig.getNameKey());
        //选中状态: 1-选中，0-未选中
        config.setNameKey(treeKeyConfig.getSelectedKey());
        //排序字段
        config.setWeightKey(treeKeyConfig.getWeightKey());
        //孩子节点
        config.setChildrenKey("children");
        //递归深度
        config.setDeep(deep);
        return config;
    }

    /***
     * 构建树形菜单
     * @param requests 需要树形结构的对象需实现 TreeNodeRequest 接口实现接口内的方法
     * @param deep 树形菜单的深度
     * @return
     */
    public static List<Tree<Integer>> buildTree(List<? extends TreeNodeRequest> requests,Integer deep,TreeKeyConfig treeKeyConfig){
        Assert.notNull(treeKeyConfig,"树形菜单键名对象不能为空");
        return TreeUtil.build(requests, 0,getTreeNodeConfig(deep, treeKeyConfig) , ((menu, treeNode) -> {
            treeNode.putExtra(treeKeyConfig.getIdKey(), menu.getTreeId());
            treeNode.putExtra(treeKeyConfig.getCodeKey(), menu.getTreeCode());
            treeNode.putExtra(treeKeyConfig.getNameKey(), menu.getTreeName());
            treeNode.putExtra(treeKeyConfig.getParentIdKey(), menu.getTreeParentId());
            treeNode.putExtra(treeKeyConfig.getTypeKey(), menu.getTreeType());
            treeNode.putExtra(treeKeyConfig.getSortKey(), menu.getTreeSort());
            treeNode.putExtra(treeKeyConfig.getSelectedKey(), menu.getTreeSelected());
        }));
    }

    public static <T> String getName(SerializableFunction<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);

        // 从lambda信息取出method、field、class等
        String fieldName = serializedLambda.getImplMethodName().substring("get".length());
        fieldName = fieldName.replaceFirst(fieldName.charAt(0) + "", (fieldName.charAt(0) + "").toLowerCase());

        return fieldName;
    }
}
