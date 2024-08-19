package com.street.one.manage.common.utils;

import com.street.one.manage.common.constants.CommonConstants;
import com.street.one.manage.common.resp.TokenInfoResp;
import com.google.common.collect.Maps;
import io.jsonwebtoken.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: JwtUtil
 * @Author: tjl
 * @Description: JWT工具类
 * @Date: 2024/5/13 16:50
 * @modified modify person name
 * @Version: 1.0
 */
public class JwtUtil {

    public static final String SECRET = "SHHS@2024";



    public static void main(String[] args) {
        TokenInfoResp tokenInfo = createJWT(CommonConstants.EXPIRED_TIME, "platform", "admin");
        System.out.println(tokenInfo.getToken());
//        Claims claims = parseJWT(tokenInfo.getToken());
//        System.out.println("claims:" + claims.toString());
//        System.out.println("是否一致:" + isVerify(tokenInfo.getToken()));

//        System.out.println("开始获取新 TOKEN");
//        TokenInfo refreshTokenInfo = refreshToken(tokenInfo.getToken(), CommonConstants.TOKEN_EXPIRED_TIME);
//        System.out.println("refreshToken:" + refreshTokenInfo.getToken());
//        Claims refreshClaims = parseJWT(refreshTokenInfo.getToken());
//        System.out.println("refreshClaims:" + refreshClaims.toString());
//        System.out.println("是否一致:" + isVerify(refreshTokenInfo.getToken()));
    }


    /**
     * 系统内部token后生成Jwt 使用Hs256算法 私匙默认SECRET 变量
     *
     * @param ttlMillis jwt过期时间
     * @param  secretType SecretTypeEnum 中取
     * @return
     */
    public static TokenInfoResp createJWT(long ttlMillis, String secretType, String code) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        TokenInfoResp tokenInfo = new TokenInfoResp();
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = Maps.newHashMap();
        //密钥
        claims.put("secret", secretType);
        //用户代码
        claims.put("accountCode",code);

        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret,
        // 那就意味着客户端是可以自我签发jwt了。
        byte[] secret = getSecret(SECRET);
        // 生成签发人
        String subject = "shHs";

        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secret);

        return checkTokenMillis(ttlMillis, tokenInfo, nowMillis, builder);
    }

    public static TokenInfoResp refreshToken(String token, long ttlMillis) {
        TokenInfoResp tokenInfo = new TokenInfoResp();
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret,
        // 那就意味着客户端是可以自我签发jwt了。
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        byte[] secret = getSecret(SECRET);
        Claims claims = parseJWT(token);
        JwtBuilder builder = Jwts.builder().signWith(signatureAlgorithm, secret).setClaims(claims);
        return checkTokenMillis(ttlMillis, tokenInfo, nowMillis, builder);
    }

    /***
     * 校验token时间
     * @param ttlMillis
     * @param tokenInfo
     * @param nowMillis
     * @param builder
     * @return
     */
    private static TokenInfoResp checkTokenMillis(long ttlMillis, TokenInfoResp tokenInfo, long nowMillis, JwtBuilder builder) {
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 设置过期时间
            builder.setExpiration(exp);
            tokenInfo.setTokenExp(expMillis);
        }
        tokenInfo.setToken(builder.compact());
        return tokenInfo;
    }


    /***
     * 获取密钥
     * @param secret
     * @return
     */
    private static byte[] getSecret(String secret) {
        byte[] key = secret.getBytes();
        try {
            key = secret.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("获取密钥失败:" + e);
        }
        return key;
    }


    /**
     * 获取自定义信息
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(getSecret(SECRET)).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static Boolean isVerify(String token) {
        // 不管是否过期，都返回claims对象
        if (token == null || token.isEmpty()) {
            return false;
        }
        Claims claims = parseJWT(token);
        Date expiration = claims.getExpiration();
        // 和当前时间进行对比来判断是否过期
        return new Date(System.currentTimeMillis()).before(expiration);
    }
}
