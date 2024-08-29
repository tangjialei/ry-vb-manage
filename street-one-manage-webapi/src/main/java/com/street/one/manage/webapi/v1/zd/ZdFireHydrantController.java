package com.street.one.manage.webapi.v1.zd;

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
 * @Package: com.street.one.manage.webapi.v1.zd
 * @ClassName: ZdFireHydrantController
 * @Author: tjl
 * @Description: 总队 灭火救援_模型表_消防栓信息 API
 * @Date: 2024/8/26 15:59
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/zd/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"消防栓信息API"})
public class ZdFireHydrantController {



    @ApiOperation("获取街镇的消火栓总数，及水压异常总和")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_zd_fire_hydrant_point_info", method = { RequestMethod.GET })
    public BaseResponse getZdFireHydrantPointInfo(@RequestParam String areaStreetCode) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/zd/get_zd_fire_hydrant_point";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("areaStreetCode",areaStreetCode);

        return HttpUtils.sentGet(ipPrefix,null,reqParams);
    }

    @ApiOperation("获取消防栓信息")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_zd_fire_hydrant_info", method = { RequestMethod.GET })
    public BaseResponse getZdFireHydrantInfo(@RequestParam String code) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/zd/get_zd_fire_hydrant_info";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("hydrantNo",code);

        return HttpUtils.sentGet(ipPrefix, null, reqParams);
    }


}
