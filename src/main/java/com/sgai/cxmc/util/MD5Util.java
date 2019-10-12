package com.sgai.cxmc.util;


/**
 * @Author: 李锐平
 * @Date: 2019/7/31 11:25
 * @Version 1.0
 */



import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;

@Slf4j
public class MD5Util {

    private static final int MD5_TIMES_16 = 16; // 加密次数为16次

    /**
     * 对字符串MD5加密，输出为消息
     * @param s
     * @return
     */
    public final static String encrypt(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            log.error("generate md5 error, {}", s, e);
            return null;
        }
    }


    /**
     * MD5 多次加密
     * @param str 待加密的字符串
     * @param times 加密次数
     * @return
     */
    public final static String encrypt(String str,int times) {

        if(StringUtils.isEmpty(str)) {
            return str;
        }
        if(times<=0){
            times = 1;
        }
        for (int i = 0; i < times; i++) {
            str = encrypt(str);
        }
        return str ;
    }


    public final static String encryptRepeat16(String str) {
        return encrypt(str,MD5_TIMES_16);
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
        System.out.println(encrypt("123456",32));
    }
}

