package com.street.one.manage.webapi.v1.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.street.one.manage.common.annotation.AuthUrl;
import com.street.one.manage.common.constants.ThirdConfigConstants;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.core.domain.manager.ThirdConfigManager;
import com.street.one.manage.common.enums.SecretTypeEnum;
import com.street.one.manage.common.exception.BusinessException;
import com.street.one.manage.common.utils.BaseResponseUtil;
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
 * @ClassName: VillageController
 * @Author: tjl
 * @Description: 小区api
 * @Date: 2024/8/23 13:35
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/village/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"小区API"})
public class VillageController {


    @ApiOperation("获取小区分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_village_page_list", method = { RequestMethod.POST })
    public BaseResponse getVillagePageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }

        JSONObject req = JSON.parseObject(body);
        String streetName = req.getString("streetName");
        if(StringUtil.isEmptyOrNull(streetName)){
            return BaseResponseUtil.fail("街镇名称不能为空");
        }

        ipPrefix += "v1/basic/location/get_village_page";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


    @ApiOperation("获取小区详情")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_village_detail", method = { RequestMethod.GET })
    public BaseResponse getVillageDetail(String villageCode) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/location/get_village_basic";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("villageCode",villageCode);

        return HttpUtils.sentGet(ipPrefix, null, reqParams);
    }


    @ApiOperation("根据小区代码统计小区设施")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_village_facility_list", method = { RequestMethod.GET })
    public BaseResponse getVillageFacilityList(String villageCode) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/location/get_village_facility_count";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("villageCode",villageCode);

        return HttpUtils.sentGet(ipPrefix, null, reqParams);
    }

}
