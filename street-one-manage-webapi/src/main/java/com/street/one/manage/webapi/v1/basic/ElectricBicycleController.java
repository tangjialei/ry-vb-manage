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
 * @ClassName: ElectricBicycleController
 * @Author: tjl
 * @Description: 电动自行车棚充电设施 api
 * @Date: 2024/8/23 10:15
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/electric/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"电动自行车棚充电设施API"})
public class ElectricBicycleController {


    @ApiOperation("获取电动自行车棚充电设施分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_electric_bicycle_page_list", method = { RequestMethod.POST })
    public BaseResponse getElectricBicyclePageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/electric/get_electric_bicycle_page";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


    @ApiOperation("获取电动自行车棚充电设施详情分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_electric_bicycle_detail_page_list", method = { RequestMethod.POST })
    public BaseResponse getElectricBicycleDetailPageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/electric/get_electric_bicycle_detail_page_list";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }



}
