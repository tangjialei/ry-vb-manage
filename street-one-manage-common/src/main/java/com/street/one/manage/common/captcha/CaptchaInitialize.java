package com.street.one.manage.common.captcha;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import com.street.one.manage.common.constants.CommonConstants;
import java.awt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config.captcha
 * @ClassName: CaptchaInit
 * @Author: tjl
 * @Description: 验证码初始化配置
 * @Date: 2024/6/4 14:02
 * @modified modify person name
 * @Version: 1.0
 */
@Configuration
public class CaptchaInitialize {

    @Autowired
    private CaptchaConfig captchaConfig;

    /**
     * 验证码文字生成器
     *
     * @return CodeGenerator
     */
    @Bean
    public CodeGenerator codeGenerator() {
        CaptchaConfig.CodeGeneratorConfig codeGeneratorConfig = captchaConfig.getCode();
        //验证码字符类型
        String codeType = codeGeneratorConfig.getType();
        //验证码字符长度
        int codeLength = codeGeneratorConfig.getLength();
        //算数生成器
        if (CommonConstants.CODE_GENERATOR_MATH_TYPE.equalsIgnoreCase(codeType)) {
            return new MathGenerator(codeLength);
        } else if (CommonConstants.CODE_GENERATOR_MATH_RANDOM.equalsIgnoreCase(codeType)) {
            //随机数生成器
            return new RandomGenerator(codeLength);
        } else {
            throw new RuntimeException("Invalid captcha generator type: " + codeType);
        }
    }


    /**
     * 验证码字体
     */
    @Bean
    public Font captchaFont() {
        CaptchaConfig.FontConfig fontConfig = captchaConfig.getFont();
        Font font;
        switch (fontConfig.getStyle()){
            case 0:
                font = new Font(fontConfig.getName(),Font.PLAIN,fontConfig.getSize());
                break;
            case 2:
                font = new Font(fontConfig.getName(),Font.ITALIC,fontConfig.getSize());
                break;
            default:
                //默认粗体
                font = new Font(fontConfig.getName(),Font.BOLD,fontConfig.getSize());
        }
        return font;
    }

}
