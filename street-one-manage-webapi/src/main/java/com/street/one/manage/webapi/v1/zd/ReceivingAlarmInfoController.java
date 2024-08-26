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
 * @ClassName: ReceivingAlarmInfoController
 * @Author: tjl
 * @Description: 获取警情 api
 * @Date: 2024/8/20 14:33
 * @modified modify person name
 * @Version: 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/zd/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"接处警警情API"})
public class ReceivingAlarmInfoController {


    @ApiOperation("获取警情分页列表")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_receiving_alarm_main_list", method = { RequestMethod.POST })
    public BaseResponse getReceivingAlarmMainList(@RequestBody String body) {
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }
        ipPrefix += "v1/zd/get_receiving_alarm_main_list";

        return HttpUtils.sentPost(ipPrefix,null,body);
    }

    @ApiOperation("接处警详情查询")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_receiving_alarm_main_details", method = { RequestMethod.GET })
    public BaseResponse getReceivingAlarmMainDetails(@RequestParam String code){
        //获取配置信息
        String ipPrefix = ThirdConfigManager.getIpPrefix(ThirdConfigConstants.DATA_CENTER_URL);
        if(StringUtil.isEmptyOrNull(ipPrefix)){
            throw new BusinessException("获取数据运营平台配置失败,请联系管理员！");
        }

        ipPrefix += "v1/zd/get_receiving_alarm_main_details";

        Map<String,Object> reqParams = Maps.newHashMap();
        reqParams.put("code",code);

        return HttpUtils.sentGet(ipPrefix,null,reqParams);
    }

}
