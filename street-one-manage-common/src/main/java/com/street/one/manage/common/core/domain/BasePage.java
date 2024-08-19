package com.street.one.manage.common.core.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询参数
 *
 * @author tjl
 */
public class BasePage implements Serializable {

    private static final long serialVersionUID = -1404815981862687275L;

    /** 页数 */
    @NotNull(message = "分页页码不能为空！")
    private Integer pageNum;

    /** 每页大小 */
    @NotNull(message = "每页大小不能为空！")
    private Integer pageSize;

    public BasePage() {
        super();
    }

    public BasePage(Integer pageNum, Integer pageSize) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "BasePage [pageNum=" + pageNum + ", pageSize=" + pageSize + ", " + super.toString() + "]";
    }

}
