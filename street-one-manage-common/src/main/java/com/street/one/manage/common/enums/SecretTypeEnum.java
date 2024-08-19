package com.street.one.manage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.enums
 * @ClassName: SecretTypeEnum
 * @Author: tjl
 * @Description: 接口类型
 * @Date: 2024/5/13 下午4:58
 * @modified modify person name
 * @Version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum SecretTypeEnum {

    /***
     * 接口类型枚举
     */
    PLATFORM("platform", "平台接口"),
    THIRD("third", "外部接口");

    private String type;
    private String desc;


    /***
     * 根据type拿枚举
     * @param type
     * @return
     */
    public static SecretTypeEnum getEnum(String type) {
        return Arrays.stream(SecretTypeEnum.values()).
                filter(d -> d.type.equals(type)).findFirst().orElse(null);
    }

}
