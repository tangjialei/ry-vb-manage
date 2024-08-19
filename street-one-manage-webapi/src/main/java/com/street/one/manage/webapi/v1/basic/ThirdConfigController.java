package com.street.one.manage.webapi.v1.basic;


import com.street.one.manage.basic.service.IBasisThirdConfigService;
import com.street.one.manage.common.annotation.AuthUrl;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.enums.SecretTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 第三方接口规约配置表 前端控制器
 * </p>
 *
 * @author tjl
 * @since 2024-06-14
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/third/config")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"字典表相关接口"})
public class ThirdConfigController {

    @Autowired
    private IBasisThirdConfigService basisThirdConfigService;


    @ApiOperation("重置字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "reset_third_config", method = { RequestMethod.GET })
    public BaseResponse resetThirdConfig() {
        return basisThirdConfigService.resetThirdConfig();
    }

    @ApiOperation("清空字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "clear_third_config", method = { RequestMethod.GET })
    public BaseResponse clearThirdConfig() {
        return  basisThirdConfigService.clearThirdConfig();
    }

}
