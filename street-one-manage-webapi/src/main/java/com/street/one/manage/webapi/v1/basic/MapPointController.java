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
 * @ClassName: MapPointController
 * @Author: tjl
 * @Description: 地图撒点 api
 * @Date: 2024/8/27 15:52
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/street/map/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"地图撒点API"})
public class MapPointController {


    @ApiOperation("获取街镇对应的撒点数据")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_street_map_point_info", method = { RequestMethod.POST })
    public BaseResponse getStreetMapPointInfo(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/street/map/get_street_map_point";

        JSONObject jsonObject = JSON.parseObject(body);

        String streetCode = jsonObject.getString("streetCode");

        String streetName = jsonObject.getString("streetName");

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("streetCode",streetCode);
        reqParams.put("streetName",streetName);

        return HttpUtils.sentGet(ipPrefix,null,reqParams);
    }

}
