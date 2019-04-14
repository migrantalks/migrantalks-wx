package com.zgs.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES加密算法
 */
public class AESUtils {
	/**
	 * 算法类型
	 */
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int CACHE_SIZE = 1024;
    /**
     * 原AES加密、解密密钥
     */
    public static final String AES_KEY = "6A3C77BBF90A4D83";
    
    /**
     * 生成随机密钥
     * @return
     * @throws Exception
     */
    public static String getSecretKey() throws Exception {
        return getSecretKey(null);
    }
    
    /**
     * 生成密钥
     * @param seed 密钥种子
     * @return
     * @throws Exception
     */
    public static String getSecretKey(String seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom;
        if (StringUtils.isNotEmpty(seed)) {
            secureRandom = new SecureRandom(seed.getBytes());
        } else {
            secureRandom = new SecureRandom();
        }
        keyGenerator.init(KEY_SIZE, secureRandom); 
        
        SecretKey secretKey = keyGenerator.generateKey(); 
        
        return Base64.encodeBase64String(secretKey.getEncoded());
    }
    
    /**
     * 加密
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String source){
        try {
        	byte[] raw = null;
        	if(key.equals(AES_KEY)) {
        		raw = key.getBytes("ASCII");
        	} else {
        		Key k = toKey(Base64.decodeBase64(key));
        		raw = k.getEncoded(); 
        	}
			SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM); 
			Cipher cipher = Cipher.getInstance(ALGORITHM); 
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encrypted = cipher.doFinal(source.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
    }
    
    /**
     * 文件加密
     * @param key
     * @param sourceFilePath 文件源地址
     * @param destFilePath 文件目标地址
     * @throws Exception
     */
    public static void encryptFile(String key, String sourceFilePath, String destFilePath){
        try {
			File sourceFile = new File(sourceFilePath);
			File destFile = new File(destFilePath); 
			if (sourceFile.exists() && sourceFile.isFile()) {
			    if (!destFile.getParentFile().exists()) {
			        destFile.getParentFile().mkdirs();
			    }
			    destFile.createNewFile();
			    InputStream in = new FileInputStream(sourceFile);
			    OutputStream out = new FileOutputStream(destFile);
			    Key k = toKey(Base64.decodeBase64(key));
			    byte[] raw = k.getEncoded(); 
			    SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM); 
			    Cipher cipher = Cipher.getInstance(ALGORITHM); 
			    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			    CipherInputStream cin = new CipherInputStream(in, cipher);
			    byte[] cache = new byte[CACHE_SIZE];
			    int nRead = 0;
			    while ((nRead = cin.read(cache)) != -1) {
			        out.write(cache, 0, nRead);
			        out.flush();
			    }
			    out.close();
			    cin.close();
			    in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    /**
     * 解密
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String source){
        try {
        	byte[] raw = null;
        	if(key.equals(AES_KEY)) {
        		raw = key.getBytes("ASCII");
        	} else {
        		Key k = toKey(Base64.decodeBase64(key));
        		raw = k.getEncoded(); 
        	}
			SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM); 
			Cipher cipher = Cipher.getInstance(ALGORITHM); 
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

			byte[] encrypted1 = hex2byte(source);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
    }
    
    /**
     * 文件解密
     * @param key
     * @param sourceFilePath
     * @param destFilePath
     * @throws Exception
     */
    public static void decryptFile(String key, String sourceFilePath, String destFilePath){
        try {
			File sourceFile = new File(sourceFilePath);
			File destFile = new File(destFilePath); 
			if (sourceFile.exists() && sourceFile.isFile()) {
			    if (!destFile.getParentFile().exists()) {
			        destFile.getParentFile().mkdirs();
			    }
			    destFile.createNewFile();
			    FileInputStream in = new FileInputStream(sourceFile);
			    FileOutputStream out = new FileOutputStream(destFile);
			    Key k = toKey(Base64.decodeBase64(key));
			    byte[] raw = k.getEncoded(); 
			    SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM); 
			    Cipher cipher = Cipher.getInstance(ALGORITHM); 
			    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			    CipherOutputStream cout = new CipherOutputStream(out, cipher);
			    byte[] cache = new byte[CACHE_SIZE];
			    int nRead = 0;
			    while ((nRead = in.read(cache)) != -1) {
			        cout.write(cache, 0, nRead);
			        cout.flush();
			    }
			    cout.close();
			    out.close();
			    in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 转换密钥
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }
	
	/**
	 * 十六进制转换为字节
	 * @param strhex
	 * @return
	 */
	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}
	
	/**
	 * 字节转换位十六进制
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	@Test
	public void test() {
		System.out.println(encrypt(AES_KEY, "migrantalks"));
	}
}