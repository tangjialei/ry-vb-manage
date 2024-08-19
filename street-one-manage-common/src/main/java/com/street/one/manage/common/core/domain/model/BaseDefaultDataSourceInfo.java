package com.street.one.manage.common.core.domain.model;

import lombok.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.base
 * @ClassName: BaseDefaultDataSource
 * @Author: tjl
 * @Description: 默认的数据源
 * @Date: 2024/5/30 10:48
 * @modified modify person name
 * @Version: 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDefaultDataSourceInfo {

    private String id;
    private String type;
    private String url;
    private String username;
    private String password;
    private String dbname;
    private String initsize;
    private String maxactive;

}
