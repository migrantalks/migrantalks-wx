package com.zgs.utils;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * MD5加密，通过需要加密的内容 + 自定义字符串，生成加密后的字符串；使用自定义字符串是为了增加密码强度
 * @author zgs
 */
public class MD5Util {

    protected MD5Util(){ }

    private static final String SALT = "migrantalks";

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 2;

    /**
     * 加密
     * @param pwd
     * @return
     * @throws Exception
     */
    public static String encrypt(String pwd) throws Exception{

        MessageDigest md5 = MessageDigest.getInstance(ALGORITH_NAME);
        Base64.Encoder base64Encoder = Base64.getEncoder();

        return base64Encoder.encodeToString(md5.digest((pwd + SALT ).getBytes("utf-8")));
    }
}
