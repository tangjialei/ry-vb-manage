package com.street.one.manage.basic.cachemode;

import com.street.one.manage.basic.entity.BasisDictCodeEntity;
import com.google.common.collect.Maps;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-one-manage
 * @Package: com.xhxf.one.manage.service.bizmode
 * @ClassName: DictCodeCacheInfo
 * @Author: tjl
 * @Description: 数据字典 本地缓存实体类
 * @Date: 2024/1/5 18:05
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class DictCodeCacheInfo implements Serializable {
    private static final long serialVersionUID = 5073117456008617992L;

    /**
     * key:dictType
     */
    private static Map<String, List<BasisDictCodeEntity>> dictCodeMaps = Maps.newConcurrentMap();


    public static void reset() {
        DictCodeCacheInfo.dictCodeMaps.clear();
    }

    public static  Map<String, List<BasisDictCodeEntity>> getDictCodeMaps() {
        return dictCodeMaps;
    }

}
