package com.street.one.manage.common.core.domain.tree;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.req
 * @ClassName: TreeNodeReq
 * @Author: tjl
 * @Description: 树形菜单统一返回参数
 * @Date: 2024/5/24 14:48
 * @modified modify person name
 * @Version: 1.0
 */
public interface TreeNodeRequest {

    /***
     * 树形菜单id
     * @return
     */
    Integer getTreeId();

    /***
     * 树形菜单代码
     * @return
     */
    String getTreeCode();
    /***
     * 树形菜单名称
     * @return
     */
    String getTreeName();
    /***
     * 树形菜单父类id
     * @return
     */
    Integer getTreeParentId();
    /***
     * 树形菜单类型
     * @return
     */
    String getTreeType();
    /***
     * 树形菜单排序
     * @return
     */
    Integer getTreeSort();
    /***
     * 树形菜单是否选中
     * @return
     */
    Boolean getTreeSelected();


}
