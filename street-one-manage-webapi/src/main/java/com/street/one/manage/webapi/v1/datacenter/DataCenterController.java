package com.street.one.manage.webapi.v1.datacenter;

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
 * @ProjectName: data-center-crm
 * @Package: com.center.crm.webapi.v1.datacenter
 * @ClassName: DataCenterController
 * @Author: tjl
 * @Description: 数据中枢API
 * @Date: 2024/8/19 14:45
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/datacenter/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"数据中枢API"})
public class DataCenterController {


    /***
     * 星环数据湖(数据中枢)
     * @return
     */
    @ApiOperation("按区丶街道丶小区与标签统计实有人口数")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_area_population_number", method = {RequestMethod.GET})
    public BaseResponse getAreaPopulationNumber(@RequestParam String streetCode) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/basic/datacenter/get_area_population_number";

        //请求参数
        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("streetCode",streetCode);

        return HttpUtils.sentGet(ipPrefix, null, reqParams);
    }


}
