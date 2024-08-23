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
 * @ClassName: JurisdictionController
 * @Author: tjl
 * @Description: 辖区概况 api
 * @Date: 2024/8/23 14:01
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/jurisdiction/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"辖区概况API"})
public class JurisdictionOverviewController {



    @ApiOperation("获取辖区概况类型列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_jurisdiction_overview_type_info", method = { RequestMethod.POST })
    public BaseResponse getJurisdictionOverviewTypeInfo(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/jurisdiction/get_jurisdiction_overview_type_info";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


}
