package com.street.one.manage.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.street.one.manage.basic.entity.BasisThirdConfigEntity;
import com.street.one.manage.basic.mapper.BasisThirdConfigMapper;
import com.street.one.manage.basic.service.IBasisThirdConfigService;
import com.street.one.manage.common.core.domain.BaseResponse;
import com.street.one.manage.common.core.domain.manager.ThirdConfigManager;
import com.street.one.manage.common.core.domain.model.ThirdConfigInfo;
import com.street.one.manage.common.utils.BaseResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 第三方接口规约配置表 服务实现类
 * </p>
 *
 * @author tjl
 * @since 2024-06-14
 */
@Service
@Slf4j
public class BasisThirdConfigServiceImpl extends ServiceImpl<BasisThirdConfigMapper, BasisThirdConfigEntity> implements IBasisThirdConfigService {


    @Autowired
    private ThirdConfigManager thirdConfigManager;

    @PostConstruct
    public void init() throws Exception {
        loadThirdConfigCache();
    }

    @Override
    public void loadThirdConfigCache() {
        log.info("==============第三方配置缓存加载==============");
        List<BasisThirdConfigEntity> thirdConfigList = this.baseMapper.selectList(new LambdaQueryWrapper<BasisThirdConfigEntity>().eq(BasisThirdConfigEntity::getStatus, "ST002"));
        if(!CollectionUtils.isEmpty(thirdConfigList)){
            for (BasisThirdConfigEntity basisThirdConfigEntity : thirdConfigList) {
                ThirdConfigInfo thirdConfigInfo = new ThirdConfigInfo();
                BeanUtils.copyProperties(basisThirdConfigEntity,thirdConfigInfo);
                thirdConfigManager.addThirdConfig(thirdConfigInfo);
            }
            log.info("==============第三方配置缓存加载完成==============");
        }
    }

    @Override
    public BaseResponse resetThirdConfig() {
        thirdConfigManager.reset();
        this.loadThirdConfigCache();
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse clearThirdConfig() {
        thirdConfigManager.reset();
        return BaseResponseUtil.success();
    }

    @Override
    public BaseResponse addThirdConfig() {
        return null;
    }

}
