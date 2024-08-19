package com.street.one.manage.webapi.v1.basic;


import com.street.one.manage.basic.service.IBasisDictCodeService;
import com.street.one.manage.common.annotation.AuthUrl;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.enums.SecretTypeEnum;
import com.street.one.manage.common.utils.BaseResponseUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author tjl
 * @since 2024-01-05
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/basic/dict/")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(tags = {"字典表相关接口"})
public class DictCodeController {

    @Autowired
    private IBasisDictCodeService basisDictCodeService;


    @ApiOperation("根据字典类型获取详情")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "get_dict_code_by_type", method = { RequestMethod.GET })
    public BaseResponse getDictByCode(@RequestParam String dictType) {
        return BaseResponseUtil.success(basisDictCodeService.getDictCodeCacheList(Lists.newArrayList(dictType)));
    }

    @ApiOperation("重置字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "reset_dict_code_cache", method = { RequestMethod.GET })
    public BaseResponse resetDictCodeCache() {
        return basisDictCodeService.resetDictCode();
    }

    @ApiOperation("清空字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "clear_dict_code_cache", method = { RequestMethod.GET })
    public BaseResponse clearDictCodeCache() {
        return  basisDictCodeService.clearDictCodeCache();
    }


    @ApiOperation("重置字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "reset_dict_code_by_type", method = { RequestMethod.GET })
    public BaseResponse resetDictCodeByType(@RequestParam String dictType) {
        return  basisDictCodeService.resetDictCodeByType(dictType);
    }

    @ApiOperation("删除指定字典缓存")
    @AuthUrl(thirdType = {SecretTypeEnum.PLATFORM})
    @RequestMapping(value = "remove_dict_code_by_type", method = { RequestMethod.GET })
    public BaseResponse removeDictCodeByType(@RequestParam String dictType) {
        return  basisDictCodeService.removeDictCodeByType(dictType);
    }


}
