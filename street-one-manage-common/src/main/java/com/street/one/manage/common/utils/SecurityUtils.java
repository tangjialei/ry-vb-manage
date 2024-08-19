package com.street.one.manage.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: SecurityUtil
 * @Author: tjl
 * @Description: 加密工具类
 * @Date: 2023/6/25 10:35
 * @modified modify person name
 * @Version: 1.0
 */
public class SecurityUtils {

    private static String ENCODING = "UTF-8";
    public static final String DES = "DES";
    /** DES */
    public static int KEYSIZEDES = 0;

    /**
     * BASE64编码
     *
     * @param content
     * @return
     */
    public static String getBASE64Encode(String content) throws UnsupportedEncodingException {
        return Base64.encodeBase64String(content.getBytes(ENCODING));
    }

    /**
     * BASE64解码
     *
     * @param content
     * @return
     */
    public static String getBASE64Decode(String content) {
        return new String(Base64.decodeBase64(content));
    }

    /**
     * MD5加密，并按指定编码返回字符串
     *
     * @param input
     * @param encoding
     * @return
     */
    public static String getEncryptMD5(String input, String encoding) {
        byte[] bytes = new byte[0];
        try {
            bytes = getEncryptMD5Bytes(input);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(bytes, Charset.forName(encoding));
    }

    /**
     * MD5加密，并按BASE64编码返回字符串
     *
     * @param input
     * @return
     */
    public final static String getEncryptMD5(String input) {
        byte[] bytes = new byte[0];
        try {
            bytes = getEncryptMD5Bytes(input);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * MD5加密，并按16进制编码返回字符串
     *
     * @param input
     * @return
     */

    public static String getEncryptMD5HexEncoding(String input) {
        try {
            return getEncryptMD5HexEncoding(input, false);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String getEncryptMD5HexEncoding(String input, boolean isUpper) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = getEncryptMD5Bytes(input);
        return byte2hex(bytes, isUpper);
    }

    public static String byte2hex(byte[] bytes, boolean isUpper) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            if (isUpper) {
                hex = hex.toUpperCase();
            }
            sign.append(hex);
        }

        return sign.toString();
    }

    /**
     * MD5加密
     *
     * @param input
     * @return
     */
    public static byte[] getEncryptMD5Bytes(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] inputBytes = input.getBytes(ENCODING);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(inputBytes);
        return messageDigest.digest();
    }

    /**
     * 用md5生成内容摘要，再用RSA的私钥加密，进而生成数字签名
     *
     * @param privateKey
     * @param content
     * @return
     */

    public static String getMD5WithRSASign(String privateKey, String content) {
        // 验证传入的字符串
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        try {
            byte[] contentBytes = content.getBytes(ENCODING);
            Signature signature = Signature.getInstance("MD5withRSA");

            byte[] privateByte = Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateByte);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            signature.initSign(privateK);

            signature.update(contentBytes);
            byte[] signs = signature.sign();
            return Base64.encodeBase64String(signs);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return "";
    }


    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原数据,注:原数据长度小于8位时,生成的是16位长度的密文;原数据大于等于8位时,生成的是32位长度的密文
     * @return
     */
    public static String desEncode(String res) {
        return keyGeneratorES(res, DES, "", KEYSIZEDES, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @return
     */
    public static String desDecode(String res) {
        return keyGeneratorES(res, DES, "", KEYSIZEDES, false);
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private static String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            // DES 要求密钥长度为 256
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            if (keysize == 0) {
                byte[] keyBytes = ENCODING == null ? key.getBytes() : key.getBytes(ENCODING);
                random.setSeed(keyBytes);
                kg.init(56, random);
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = ENCODING == null ? key.getBytes() : key.getBytes(ENCODING);
                random.setSeed(keyBytes);
                kg.init(keysize, random);
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = ENCODING == null ? res.getBytes() : res.getBytes(ENCODING);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /** 将16进制转换为二进制 */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 根据指定长度生成字母和数字的随机数
     * <p>
     * 0~9的ASCII为48~57; A~Z的ASCII为65~90; a~z的ASCII为97~122
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        return getStringRandom(length, false);
    }

    public static String getStringRandom(int length, boolean isUpAndLow) {
        String valStr = "";
        Random random = new Random();
        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = 65;// 保证只会产生65~90之间的整数
                if (isUpAndLow) {
                    temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                }
                valStr += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                valStr += String.valueOf(random.nextInt(10));
            }
        }
        return valStr;
    }

    public static void main(String[] args) {
        String date = DateUtil.format(DateUtil.getNow());
        // 获取一个随机的四位数[1000,9999]
        Random random = new Random();
        int number = random.nextInt(9999) % (9999 - 1000 + 1) + 1000;
        String appKey = "onestore";
        String secret = "zhimeng2019";

        JSONObject dataContent = new JSONObject();
        // 组装过滤条件
        Map<String, Object> filterFields = new HashMap<>();
        dataContent.put("filter", filterFields);
        // 组装排序条件
        Map<String, Object> orderFields = new HashMap<>();
        dataContent.put("order", orderFields);
        // 分页字段组拼
        dataContent.put("page", 1);
        dataContent.put("pageSize", 10);
        /**
         * 生成签名内容
         */
        String signDataStr = String.format("AppKey=%s&Secret=%s&Number=%s&Date=%s&Data=%s", appKey, secret, number,
                date, dataContent.toString());
        System.out.println("content:::" + signDataStr);
        // 此处为demo值，实际场景需要替换为正确的值
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCWia10Y5S/CxaADHuHiFMNUvg0z/7LuRYyDNHoDQnQnSDqORsP3G16lOObh5GMz9CXTFwJ+VHyYgqMwlxDiN1SVukIM6qtZIflc4ifCU3YFTtbrIORJpyJencFkgT86xwb8m6WEWPL7XXF41Gf/qofWxoI/y/EeiGxgxCSME/7A0B/rFrRLWywoUHX9yYe0CcQQ/yw+/fqlCi58fXL4fpoZhd+GBdu1R/SNI080qEvGuCO3yO4Ip1VT2FBP1BvxUBaduFzCMb9RI8OJPBblsO4K/RVXOnTtMw+wo9uMKACVQRMixdRj9oy6RCjmtFQ1NWMVcJ+ntl3UD1cbCAeT5AXAgMBAAECggEAdP/ciDE9Up5ThoItIFGb+ycZMjLWXZAk6BF+s1Qu5Ky7AFAR8GnpzPQbUq4zObXq8Syv/DCCuFfqyyvkebjOC0jxJSbC6LDRzkvVS4X3spIN6FpUh2PzFgbQqsuqhMhWuIHb4VbysXSFrJ/YuPk9T9GGkqeDgLlLpqOvJW/XOcc+tGriIwyVu2eh77Z+znJrWrEZYUNQ5RdoZML2WJhTxhykpH3BTbOWB4XttCeMeyFickaaEKn6544WjOyvnEjFgMyD5xqjVnZSOt8bfvmU7dSJUp44Ir1F/L7q2vvN8zl9BRXk/mKPh/N+hHqOa5fvWyDjt5Hq9CqVpTXQLbvMAQKBgQDs4OAB+b46QjeUqGXYunln7uJqucmn4Ukw1VnxJDscddkSTvwnd08n48sHfDjSi1D7Zj3KsVZHq/CIehpTvbAnlru04uYtJrM9U82DOqi/Q0DrmpYpkVB3U3Y/ktLPuaJ/YI4gUv9x/qiFxlWBZGQiGdSCjLSdyk+nH5ivYf/n1wKBgQCisI/XoCfdg3RYRXAtzerH6PzzdNxrQS3zh5PSLUR1LM8lvwoOYu7FLI2FnwqgL9dqv830wNEuiDwTtOoPgf4W06SiZHozTdBw9oZv1Qh0RrZoL58DKNVFOmbFNmcOfVTgCe2OEm3CQYFdRPXEfnD3tDPQ+HDBDZe3kgxOgFeRwQKBgFTd3GPYzkahIrin/JVBee26sHP8TDtRg59zUN4MVQFFV5gvpDefj0SDYGI2J0WHlGP9pXXRQ1A6UdsTpAsMkugcIPQVttHGY8E9HQw/H+YmekPvBo2t+l5GEL+GIvE5WPa56TG7irmUu1vySwAor5doGbMMJknCCfeeYl2zhrPRAoGARVazN9eOph6My8JsmVkWI7XteWXpO90/1HeITR/1+Wrrrs7JXnBUC719Y+HxCWzahyjQguxbO1XdJRkKWSmyPdBebsgcPRmBKWQo0CkfvU8WFzmUsCgLHcJSYuNY8RgBwKr6kH7SQOTiWfo2NVUemLQ5EWuRwvYkH50jmaGhsQECgYBn+gTUZGymex5BHsYUj7pdBwAXF88P5EwS5i71S0g1sE5GlOtsxBlpaPXIwJajWSS8wuI8Wmz+0T4w205bUR0Gqu5WtF8j/Cp3npoZSDZ3J6Xs92ibH8JZH8dnA91S9hDcD+iGEnkXQyUQtyMFYBOrbfXevy9AahqDy4wuMszz0g==";
        String sign = getMD5WithRSASign(privateKey, signDataStr);
        System.out.println("sign:::" + sign);

        /**
         * 首次需要通过私钥获取公钥，然后每次的接口请求传送公钥信息
         */
        String publicKey = RSAUtil.getPublicKeyByPrivateKey(privateKey);
        JSONObject postData = new JSONObject();
        postData.put("appKey", appKey);
        postData.put("secret", secret);
        postData.put("publicKey", publicKey);
        postData.put("number", number);
        postData.put("date", date);
        postData.put("dataContent", dataContent.toString());
        postData.put("sign", sign);
        System.out.println("请求接口参数:::" + postData);
    }
}
