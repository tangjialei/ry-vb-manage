package com.street.one.manage.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.street.one.manage.basic.entity.BasisDictCodeEntity;
import com.street.one.manage.common.core.domain.BaseResponse;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author tjl
 * @since 2024-01-05
 */
public interface IBasisDictCodeService extends IService<BasisDictCodeEntity> {

    /**
     * 加载参数缓存数据
     */
    void loadDictCodeCache();

    /***
     * 重置缓存数据
     * @return
     */
    BaseResponse resetDictCode();

    /***
     * 清除缓存数据
     * @return
     */
    BaseResponse clearDictCodeCache();

    /***
     * 新增缓存
     * @return
     */
    BaseResponse addDictCodeCache();


    /****
     * 重置指定缓存
     * @param dictType
     * @return
     */
    BaseResponse resetDictCodeByType(String dictType);

    /***
     * 删除指定缓存
     * @param dictType
     * @return
     */
    BaseResponse removeDictCodeByType(String dictType);


    /**
     * 根据字典类型获取字典数据
     * @param dictTypes 字典类型
     * @return
     */
    List<BasisDictCodeEntity> getDictCodeCacheList(List<String> dictTypes);


    /***
     * 根据字典代码查询字典详情
     * @param dictCode
     * @return
     */
    BasisDictCodeEntity getDictCodeInfo(String dictCode);



}
