package com.street.one.manage.job.thirdmode;

import com.street.one.manage.common.core.domain.model.AbstractInfo;
import com.street.one.manage.common.utils.BizBaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.task.bizmode
 * @ClassName: ThirdConvertInfo
 * @Author: tjl
 * @Description: 第三方转化对象
 * @Date: 2023/7/14 上午10:10
 * @modified modify person name
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThirdConvertInfo extends BizBaseResult {
    private static final long serialVersionUID = -7469567634627203665L;
    /***
     * 转换成功的数据
     */
    List<AbstractInfo> successInfos = new ArrayList<>();
    /***
     * 转换失败的数据
     */
    List<AbstractInfo> errorInfos = new ArrayList<>();

    public static ThirdConvertInfo success(String msg, List<? extends AbstractInfo> successInfos, List<? extends AbstractInfo> errorInfos) {
        ThirdConvertInfo info = new ThirdConvertInfo();
        info.setSuccess(true);
        info.setMsg(msg);
        info.setSuccessInfos(new ArrayList<>(successInfos));
        info.setErrorInfos(new ArrayList<>(errorInfos));
        return info;
    }

    public static ThirdConvertInfo error(String msg) {
        ThirdConvertInfo info = new ThirdConvertInfo();
        info.setSuccess(false);
        info.setMsg(msg);
        return info;
    }
}
