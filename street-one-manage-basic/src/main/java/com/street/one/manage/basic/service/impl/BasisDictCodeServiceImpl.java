package com.street.one.manage.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.street.one.manage.basic.cachemode.DictCodeCacheInfo;
import com.street.one.manage.basic.entity.BasisDictCodeEntity;
import com.street.one.manage.basic.mapper.BasisDictCodeMapper;
import com.street.one.manage.basic.service.IBasisDictCodeService;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.utils.Assert;
import com.street.one.manage.common.utils.BaseResponseUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author tjl
 * @since 2024-01-05
 */
@Service
@Slf4j
public class BasisDictCodeServiceImpl extends ServiceImpl<BasisDictCodeMapper, BasisDictCodeEntity> implements IBasisDictCodeService {

    /***
     * 项目启动初始化字典
     */
    @PostConstruct
    public void init() {
        loadDictCodeCache();
    }

    @Override
    public void loadDictCodeCache() {
        log.info("==============数据字典缓存加载==============");
        DictCodeCacheInfo.reset();
        List<BasisDictCodeEntity> dictCodeList = this.baseMapper.selectList(new LambdaQueryWrapper<>());
        Map<String, List<BasisDictCodeEntity>> dictCodeMap = Maps.newHashMap();
        if(!CollectionUtils.isEmpty(dictCodeList)){
            dictCodeMap = dictCodeList.stream().sorted(Comparator.comparing(BasisDictCodeEntity::getCode).reversed()).collect(Collectors.groupingBy(BasisDictCodeEntity::getDictType));
        }
        for (String dictType : dictCodeMap.keySet()) {
            List<BasisDictCodeEntity> values = dictCodeMap.get(dictType);
            if(!CollectionUtils.isEmpty(values)){
                DictCodeCacheInfo.getDictCodeMaps().put(dictType,values);
            }
        }
        log.info("==============数据字典缓存加载完成==============");
    }


    @Override
    public BaseResponse resetDictCode() {
        this.clearDictCodeCache();
        this.loadDictCodeCache();
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse clearDictCodeCache() {
        DictCodeCacheInfo.reset();
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse addDictCodeCache() {
        //需要使用在实现
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse resetDictCodeByType(String dictType) {
        Assert.notEmptyOrNull(dictType,"字典类型不能为空！");
        Set<String> dictTypeKey = DictCodeCacheInfo.getDictCodeMaps().keySet();
        if(dictTypeKey.contains(dictType)){
            LambdaQueryWrapper<BasisDictCodeEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(BasisDictCodeEntity::getDictType,dictType);
            List<BasisDictCodeEntity> dictCodeList = this.baseMapper.selectList(wrapper);
            if(!CollectionUtils.isEmpty(dictCodeList)){
                DictCodeCacheInfo.getDictCodeMaps().put(dictType,dictCodeList);
            }
        }
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse removeDictCodeByType(String dictType) {
        Assert.notEmptyOrNull(dictType,"字典类型不能为空！");
        Set<String> dictTypeKey = DictCodeCacheInfo.getDictCodeMaps().keySet();
        if(dictTypeKey.contains(dictType)){
            DictCodeCacheInfo.getDictCodeMaps().remove(dictType);
        }
        return BaseResponseUtil.success();
    }

    @Override
    public List<BasisDictCodeEntity> getDictCodeCacheList(List<String> dictTypes) {
        Assert.notCollectNull(dictTypes,"字典类型集合不能为空！");
        List<BasisDictCodeEntity> basisDictCodeList = Lists.newArrayList();
        for (String dictType : dictTypes) {
            List<BasisDictCodeEntity> dictCodeList = DictCodeCacheInfo.getDictCodeMaps().get(dictType);
            if(!CollectionUtils.isEmpty(dictCodeList)){
                basisDictCodeList.addAll(dictCodeList);
            }
        }
        return basisDictCodeList;
    }

    @Override
    public BasisDictCodeEntity getDictCodeInfo(String dictCode) {
        Assert.notEmptyOrNull(dictCode,"字典代码不能为空！");
        return this.baseMapper.selectOne(new QueryWrapper<BasisDictCodeEntity>().lambda().eq(BasisDictCodeEntity::getDictCode,dictCode));
    }
}
