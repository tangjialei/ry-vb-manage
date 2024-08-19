package com.street.one.manage.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.street.one.manage.basic.entity.BasisThirdConfigEntity;
import com.street.one.manage.common.core.domain.BaseResponse;

/**
 * <p>
 * 第三方接口规约配置表 服务类
 * </p>
 *
 * @author tjl
 * @since 2024-06-14
 */
public interface IBasisThirdConfigService extends IService<BasisThirdConfigEntity> {

    /**
     * 加载第三方配置缓存数据
     */
    void loadThirdConfigCache();

    /***
     * 重置第三方配置缓存数据
     * @return
     */
    BaseResponse resetThirdConfig();

    /***
     * 清除第三方配置缓存数据
     * @return
     */
    BaseResponse clearThirdConfig();

    /***
     * 新增第三方配置缓存
     * @return
     */
    BaseResponse addThirdConfig();



}
