package com.street.one.manage.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: EncryptUtils
 * @Author: tjl
 * @Description: 加密解密
 * @Date: 2024/5/13 16:50
 * @modified modify person name
 * @Version: 1.0
 */
public class EncryptUtils {

    /**
     * DES的加密解密代码
     */
    private static final String DES_KEY = "WeChatNewTouch@#0!";
    private static final String DES = "DES";
    @SuppressWarnings("restriction")
    public static String encryptBasedDes(String data) {
        String encryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY.getBytes("GBK"));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            // log.error("加密错误，错误信息：", e);
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }

    public static String decryptBasedDes(String cryptData) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(DES_KEY.getBytes("GBK"));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串进行解码，解码为为字节数组，并解密
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
        } catch (Exception e) {
            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }

    public static void main(String[] args) {
        System.out.println(encryptBasedDes("wx011eb75b383f49a6"));
        System.out.println(encryptBasedDes("56122b85c1319eeac50ded058975b18c"));
//        System.out.println(decryptBasedDes("KJ2g4jcYYBWRECSPmDMc+42q4zC2gWia"));
//        System.out.println(decryptBasedDes("ZZj+P7UfhjZywk0qrFGjf8MBUHuYqkvPWQ8gGkS42yRcsnvRdEQ5oA=="));
//        System.out.println(encryptBasedDes("wx2c1b4a1a0cb7d54e"));
//        System.out.println(encryptBasedDes("fdc210daab95b9f7958caebd63ffd134"));
    }
}
