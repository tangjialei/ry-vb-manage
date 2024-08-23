package com.street.one.manage.webapi.v1.basic;

import com.google.common.collect.Maps;
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
 * @ClassName: StreetController
 * @Author: tjl
 * @Description: 街道 api
 * @Date: 2024/8/20 17:00
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/committee/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"居委API"})
public class CommitteeController {


    @ApiOperation("获取居委信息分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_committee_page_list", method = { RequestMethod.POST })
    public BaseResponse getCommitteePageList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/location/get_committee_page";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }


    @ApiOperation("根据街道代码获取居委信息")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_committee_info", method = { RequestMethod.GET })
    public BaseResponse getCommitteeInfo(@RequestParam String streetCode){
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }

        ipPrefix += "v1/basic/location/get_committee_info";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("streetCode",streetCode);

        return HttpUtils.sentGet(ipPrefix,null,reqParams);
    }


    @ApiOperation("获取街镇详细列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_street_info_list", method = { RequestMethod.GET })
    public BaseResponse getStreetInfoList(){
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据中台配置失败,请联系管理员！");
        }

        ipPrefix += "v1/basic/location/get_street_info_list";

        return HttpUtils.sentGet(ipPrefix,null,null);
    }


}
