package com.street.one.manage.common.core.domain.tree;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.basic.tree
 * @ClassName: TreeKeyConfig
 * @Author: tjl
 * @Description: 树形菜单配置类的key名称接口
 * @Date: 2024/5/24 16:27
 * @modified modify person name
 * @Version: 1.0
 */
public interface TreeKeyConfig {

    /***
     * 树形菜单配置id名称
     * @return
     */
    String getIdKey();

    /***
     * 树形菜单配置父类id 名称
     * @return
     */
    String getParentIdKey();
    /***
     * 树形菜单名称
     * @return
     */
    String getNameKey();
    /***
     * 选中状态: 1-选中，0-未选中
     * @return
     */
    String getSelectedKey();

    /***
     * 自定义配置 树形 code
     * @return
     */
    String getCodeKey();

    /***
     * 自定义配置 树形 type
     * @return
     */
    String getTypeKey();

    /***
     * 排序
     * @return
     */
    String getSortKey();

    /***
     * 树形菜单类型
     * @return
     */
    String getWeightKey();
}
