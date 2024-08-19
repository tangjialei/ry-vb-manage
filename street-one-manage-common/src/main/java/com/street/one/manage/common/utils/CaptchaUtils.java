package com.street.one.manage.common.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import com.street.one.manage.common.core.domain.model.CaptchaResult;
import com.street.one.manage.common.core.domain.cache.LocalHostCache;
import com.street.one.manage.common.captcha.CaptchaConfig;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.enums.CaptchaTypeEnum;
import com.street.one.manage.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: VerificationCodeUtils
 * @Author: tjl
 * @Description: 验证码工具类
 * @Date: 2024/6/4 10:37
 * @modified modify person name
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class CaptchaUtils {

    /***
     * 验证码文字生成器
     */
    private final CodeGenerator codeGenerator;
    /***
     * 字体
     */
    private final Font captchaFon;
    /***
     * 验证码配置参数
     */
    private final CaptchaConfig captchaProperties;

    /***
     * 获取验证码
     * @return
     */
    public CaptchaResult getCaptcha() {
        //验证码类型
        String type = captchaProperties.getType();
        //验证码图片宽度
        int width = captchaProperties.getWidth();
        //验证码图片高度
        int height = captchaProperties.getHeight();
        //干扰线数量
        int interfereCount = captchaProperties.getInterfereCount();
        //验证码字符长度
        int codeLength = captchaProperties.getCode().getLength();

        AbstractCaptcha captcha;

        if (CaptchaTypeEnum.CIRCLE.name().equalsIgnoreCase(type)) {
            captcha = CaptchaUtil.createCircleCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.GIF.name().equalsIgnoreCase(type)) {
            captcha = CaptchaUtil.createGifCaptcha(width, height, codeLength);
        } else if (CaptchaTypeEnum.LINE.name().equalsIgnoreCase(type)) {
            captcha = CaptchaUtil.createLineCaptcha(width, height, codeLength, interfereCount);
        } else if (CaptchaTypeEnum.SHEAR.name().equalsIgnoreCase(type)) {
            captcha = CaptchaUtil.createShearCaptcha(width, height, codeLength, interfereCount);
        } else {
            throw new BusinessException("Invalid captcha type: " + type);
        }
        captcha.setGenerator(codeGenerator);
        captcha.setTextAlpha(captchaProperties.getTextAlpha());
        captcha.setFont(captchaFon);
        //图形验证码
        String captchaCode = captcha.getCode();
        //图形验证码,Base64连接
        String imageBase64Data = captcha.getImageBase64Data();
        //uuid
        String captchaKey  = IdUtil.fastSimpleUUID();
        //加入缓存
        //对应的缓存的超时时间 这里减少5s 避免刚好再两个小时内请求
        Long expireSeconds = captchaProperties.getExpireSeconds() - 5000;
        LocalHostCache.putValue(CommonConstants.CAPTCHA_CODE_PREFIX + captchaKey,captchaCode,expireSeconds);
        return CaptchaResult.builder().captchaKey(captchaKey).imageBase64Data(imageBase64Data).build();
    }

}
