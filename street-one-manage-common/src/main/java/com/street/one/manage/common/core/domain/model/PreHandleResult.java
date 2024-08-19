package com.street.one.manage.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.core.domain.model
 * @ClassName: PreHandleResult
 * @Author: tjl
 * @Description: 导入excel前置处理通用返回
 * @Date: 2024/6/27 16:02
 * @modified modify person name
 * @Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreHandleResult implements Serializable {
    private static final long serialVersionUID = -5810337954423529186L;


    /****
     * 表格保存服务器路径
     */
    private  String excelPath;

    /***
     * 服务器导入文件名
     */
    private  String serverExcelName;

    /***
     * 日志保存-返回的主键id
     */
    private  Integer importExcelId;

}
