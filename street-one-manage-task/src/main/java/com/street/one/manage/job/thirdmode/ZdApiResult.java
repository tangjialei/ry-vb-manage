package com.street.one.manage.job.thirdmode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.task.bizmode
 * @ClassName: xdzdResult
 * @Author: tjl
 * @Description: 徐汇总队统一返回结果集对象
 * @Date: 2023/7/14 上午9:52
 * @modified modify person name
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZdApiResult {
    /***
     * 返回状态
     */
    private int status;
    /***
     * 返回错误消息
     */
    private String message;
    /***
     * 返回数据
     */
    private Object data;

}
