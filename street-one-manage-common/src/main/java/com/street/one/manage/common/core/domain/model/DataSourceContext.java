package com.street.one.manage.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.resp
 * @ClassName: DataSourceResp
 * @Author: tjl
 * @Description: 数据源对象
 * @Date: 2024/5/30 13:17
 * @modified modify person name
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceContext implements Serializable {

    private static final long serialVersionUID = 4123535326027334518L;

    /***
     * 数据源id，取basis_sys_datasource 表的id
     */
    private String dataSourceId;

    /***
     * 数据源类型，取basis_sys_datasource 表的type
     */
    private String dbType;

    /***
     * 使用模型
     */
    private String mode;

}
