package com.street.one.manage.webapi.v1.basic;

import com.street.one.manage.common.annotation.AuthUrl;
import com.street.one.manage.common.constants.ThirdConfigConstants;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.core.domain.manager.ThirdConfigManager;
import com.street.one.manage.common.enums.SecretTypeEnum;
import com.street.one.manage.common.exception.BusinessException;
import com.street.one.manage.common.utils.HttpUtils;
import com.street.one.manage.common.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.webapi.v1.basic
 * @ClassName: VisualCenterDeviceController
 * @Author: tjl
 * @Description: 视觉中枢API
 * @Date: 2024/8/21 17:02
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/visual/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"视觉中枢API"})
public class VisualCenterDeviceController {


    @ApiOperation("获取视觉中枢视频分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_visual_center_device_page_list", method = { RequestMethod.POST })
    public BaseResponse getVisualCenterDevicePageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/visual/get_visual_center_device_page_list";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


}
