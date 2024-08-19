package com.street.one.manage.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.bizmode
 * @ClassName: CaptchaResult
 * @Author: tjl
 * @Description: 图形验证码统一返回
 * @Date: 2024/6/4 15:06
 * @modified modify person name
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResult implements Serializable {

    private static final long serialVersionUID = -4287571830468722975L;

    /***
     * 图形验证码key
     */
    private String captchaKey;

    /***
     * 图形验证码图片
     */
    private String imageBase64Data;
}
