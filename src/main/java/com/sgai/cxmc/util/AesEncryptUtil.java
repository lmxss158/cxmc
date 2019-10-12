package com.sgai.cxmc.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @Description:
 * @Author: 李锐平
 * @Date: 2019/8/2 8:43
 * @Version 1.0
 */

public class AesEncryptUtil {
//    private static final String KEY = "d7b85f6e214abcda";
    public static final String KEY = "a7b85h6e658at4da";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return Base64.decodeBase64(base64Code);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, "utf-8");
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws Exception {
        String content = "B9XPPxbAwHB9XPPxbAwHB9XPPxbAwHsd|1564732250261|asddsdw3w|"
                + "CXMC,MCJT,MCZL,MCFI,MCFIZYZB,MCFIGXCBGF,MCFICBGC,MCFICPYLNL,MCZZ,MCZZCLGL,MCZZKC,MCZZJSZB,"
                + "MCZZGJZB,MCZZGXZB,MCXS,MCXSDDGL,MCXSCPTJ,MCXSKHFW,MCWL,MCCG,MCCGCGGY,MCCGYRLKC,MCSB,MCNH,MCHJ,MCNY,"
                + "MCRL,MCRLDGLCL,MCRLQMRS,MCPRO,MCPRO1,MCPRO2,MCPRO3,MCPRO4,MCPRO5,MCQS,QSZL,SS02,SS0201,SS0202,SS0203,"
                + "SS0204,SS03,SS04,SS0401,SS0402,SS0403,SS0404,SS05,SS0501,SS0502,SS06,SS0601,SS0602,SS07,SS0701,SS0702,"
                + "SS0703,QSRZ,QSRZ01,QSRZ02,SS09";
//        System.out.println("加密前：" + content);
//        content = content.replace(",","");
//        System.out.println("加密前：" + content.length());

//        String encrypt = AesEncryptUtil.aesEncrypt(content, KEY);
//        System.out.println(encrypt.length() + ":加密后：" + encrypt);

//        String decrypt = aesDecrypt(encrypt, KEY);
//        System.out.println(decrypt.length()+":解密后：" + decrypt);

//        String a1 = "CXMCMCJTMCZLMCFIMCFIZYZBMCFIGXCBGFMCFICBGCMCFICPYLNLMCZZMCZZCLGLMCZZKCMCZZJSZBMCZZGJZBMCZZGXZBMCXSMCXSDDGLMCXSCPTJMCXSKHFWMCWLMCCGMCCGCGGYMCCGYRLKCMCSBMCNHMCHJMCNYMCRLMCRLDGLCLMCRLQMRSMCPROMCPRO1MCPRO2MCPRO3MCPRO4MCPRO5MCQSQSZLSS02SS0201SS0202SS0203SS0204SS03SS04SS0401SS0402SS0403SS0404SS05SS0501SS0502SS06SS0601SS0602SS07SS0701SS0702SS0703QSRZQSRZ01QSRZ02SS09";

//        System.out.println(a1.indexOf("CXMC"));


        String ttt = "58HcCU3CfSJ/DulMm+0MNPi8UGy4Q3dr+ry6Op2yWfK+XUFp5Jvsq8sGy+8x3toBEqLPiNMdwqnFVEbuJBQ16A3ZaWoRbWwLcPBBMdeefWgFwjsksXAyEegHPLI3jg2CnFkT+LE3Fc1viSwHUSByCVzUizzrQuwcdn7znA3vqSSXkVw6pGhPwGRCLEIkZ18aNRn1dn0HZkcO9PrbKGBG8N4TYUzT06qd8+IPK6sb6u23gLazOs1Z0mKl0c//SMpbUkNoJ2uLA5yAKN7MbZlWoTMi22S/3LwqB8l3ScTHrvkkyvb0S5aHg1zBsxpiSRS0hH1MXKjX+3hlxX8U0KKxdMXXNdEaxz45wD1mBrXIJrkVnst+F8m9C4wfewnuZJ1AE/iGlTNt1qTxO4g77r3tGRHiaGKzWyHsHcmnVFlQhCeAwAIEG9cJMHO+9yjkcduNzZkA7Cr/phtWXSYqcf2QKHklmrpKMTuYU6giUtygd16xBse9TOoNweEegedHoq8dmF7g35+tqqIzreNw4W7bQ2n2hem9qXNU/5/7rZ4V7fXbxPajTuutDeDymLZMHHcepHxeKHh9+jnSYOMbOyquKvp8VWr/XSlvs0aLuGF5ZuobnK3x6UYuBY/B9567tViw5SIiZJ+67xTWzykFwUvGowIjAShzc6vPGaE5e2zGHmtt/qGewwvCfoNIaZJ5PGlZ";
        System.out.println(AesEncryptUtil.aesDecrypt(ttt,KEY));


    }



}
