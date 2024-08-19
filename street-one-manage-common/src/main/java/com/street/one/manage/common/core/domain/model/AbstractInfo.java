package com.street.one.manage.common.core.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.task.bizmode
 * @ClassName: AbstractInfo
 * @Author: tjl
 * @Description: 抽象对象
 * @Date: 2023/7/14 上午10:09
 * @modified modify person name
 * @Version: 1.0
 */
public class AbstractInfo implements Serializable {

    private static final long serialVersionUID = -3690996752330612628L;
    /***
     * 转化异常消息
     */
    @TableField(exist = false)
    private String errorConvertMsg = null;

    public String getErrorConvertMsg() {
        return errorConvertMsg;
    }

    public void setErrorConvertMsg(String errorConvertMsg) {
        this.errorConvertMsg = errorConvertMsg;
    }
}
