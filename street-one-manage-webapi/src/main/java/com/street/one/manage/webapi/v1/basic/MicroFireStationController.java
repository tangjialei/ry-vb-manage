package com.street.one.manage.webapi.v1.basic;

import com.google.common.collect.Maps;
import com.street.one.manage.basic.service.IBasisDictCodeService;
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

import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.webapi.v1.basic
 * @ClassName: MicroFireStationController
 * @Author: tjl
 * @Description: 微型消防站 api
 * @Date: 2024/8/21 16:07
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/micro/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"微型消防站API"})
public class MicroFireStationController {

    @ApiOperation("获取微型消防站分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_micro_fire_station_page_list", method = { RequestMethod.POST })
    public BaseResponse getMicroFireStationPageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/micro/get_micro_fire_station_page_list";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


    @ApiOperation("获取微型消防站详情分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_micro_fire_station_detail_page_list", method = { RequestMethod.POST })
    public BaseResponse getMicroFireStationDetailPageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/micro/get_micro_fire_station_detail_page_list";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


    @ApiOperation("获取微型消防站信息")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_micro_fire_station_info", method = { RequestMethod.GET })
    public BaseResponse getMicroFireStationInfo(@RequestParam String code) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/micro/get_micro_fire_station_info";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("code",code);

        return HttpUtils.sentGet(ipPrefix, null, reqParams);
    }



}
