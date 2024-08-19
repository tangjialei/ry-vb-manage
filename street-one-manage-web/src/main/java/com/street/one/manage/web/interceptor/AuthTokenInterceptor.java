package com.street.one.manage.web.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.street.one.manage.common.annotation.AuthUrl;
import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.core.domain.cache.AccountContextCache;
import com.street.one.manage.common.enums.BaseResponseCodeEnum;
import com.street.one.manage.common.enums.SecretTypeEnum;
import com.street.one.manage.common.exception.BusinessException;
import com.street.one.manage.common.resp.AccountInfoResp;
import com.street.one.manage.common.utils.Assert;
import com.street.one.manage.common.utils.JwtUtil;
import com.street.one.manage.common.utils.LoginContextUtils;
import com.street.one.manage.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.interceptor
 * @ClassName: LoginTokenInterceptor
 * @Author: tjl
 * @Description: 验证用户登录token
 * @Date: 2023/6/21 17:51
 * @modified modify person name
 * @Version: 1.0
 */
@Slf4j
@Component
public class AuthTokenInterceptor extends AbstractInterceptor {

    @Value(value = "${token.flag}")
    private Boolean tokenFlag;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(tokenFlag){
            String token = request.getHeader(CommonConstants.TOKEN_HEADER_PREFIX);
            //校验参数是否存在
            if (StringUtils.isEmpty(token)) {
                log.error("=============认证参数错误!==============");
                throw new BusinessException(BaseResponseCodeEnum.E400.getCode(),
                        BaseResponseCodeEnum.E400.getMsg());
            }
            //校验token真实性
            DecodedJWT decode;
            String secretType;
            String accountCode;
            try{
                decode = JWT.decode(token);
                //解析token拿到的证书
                secretType = decode.getClaim("secret").asString();
                //用户代码
                accountCode = decode.getClaim("accountCode").asString();
            }catch (Exception e){
                throw new BusinessException(BaseResponseCodeEnum.E403.getCode(), BaseResponseCodeEnum.E403.getMsg());
            }

            //校验token是否过期
            if (!JwtUtil.isVerify(token)) {
                throw new BusinessException(BaseResponseCodeEnum.E401.getCode(),
                        BaseResponseCodeEnum.E401.getMsg());
            }
            //获取用户信息
            AccountInfoResp accountInfoResp = (AccountInfoResp) AccountContextCache.getValue(CommonConstants.CACHE_PREFIX ,accountCode,true);
            Assert.notNull(accountInfoResp,BaseResponseCodeEnum.E401);
            if(null == accountInfoResp || StringUtil.isEmptyOrNull(accountInfoResp.getToken())){
                throw new BusinessException(BaseResponseCodeEnum.E401.getCode(),
                        BaseResponseCodeEnum.E401.getMsg());
            }
            //token不同提示失效
            if(!token.equals(accountInfoResp.getToken())){
                throw new BusinessException(BaseResponseCodeEnum.E401.getCode(),
                        "该账号已在其它地址登录");
            }
            //判断用户访问url有无权限
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                //是否有注解
                if(method.isAnnotationPresent(AuthUrl.class)) {
                    AuthUrl authUrl = method.getDeclaredAnnotation(AuthUrl.class);
                    SecretTypeEnum[] secretTypeEnums = authUrl.thirdType();
                    boolean isAuth = Arrays.stream(secretTypeEnums).anyMatch(e -> e.getType().equals(secretType));
                    if(!isAuth) {
                        log.error("=============token:{},secret:{} 没有访问该资源权限!==============", token,secretType);
                        throw new BusinessException(BaseResponseCodeEnum.E1005.getCode(),BaseResponseCodeEnum.E1005.getMsg());
                    }
                }
            }else {
                log.error("=============token:{} 没有访问该资源权限!==============", token);
                throw new BusinessException(BaseResponseCodeEnum.E1005.getCode(),BaseResponseCodeEnum.E1005.getMsg());
            }
            //设置上下文
            LoginContextUtils.setAccountInfoResp(accountInfoResp);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContextUtils.remove();
    }
}
