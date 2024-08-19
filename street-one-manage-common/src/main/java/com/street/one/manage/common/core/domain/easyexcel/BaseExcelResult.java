package com.street.one.manage.common.core.domain.easyexcel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.easyexcel
 * @ClassName: BaseExcelResult
 * @Author: tjl
 * @Description: 基类，解析excel的返回结果集
 * @Date: 2024/7/2 14:59
 * @modified modify person name
 * @Version: 1.0
 */
@Getter
@Setter
public class BaseExcelResult implements Serializable {
    private static final long serialVersionUID = -8237886487424262850L;

    /***
     * excel 头部
     */
    private Map<Integer, ?> headers;

    /***
     * excel 具体数据行
     */
    private List<?> values;
}
