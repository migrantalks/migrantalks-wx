package com.zgs.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JSON Web Token
 * @author zgs
 */
public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * token有效时间为一周
     */
    private static final long expireMills = 7 * 24 * 60 * 60 * 1000;
    /**
     * 签名算法
     */
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    /**
     * 秘钥 migrantalks
     */
    private static final String SECRET_KEY = "55d1c1fecf38fb38d284b35f83b1eb4b";

    /**
     * 生成JWT
     * @return
     */
    public static String createJWT(String userName){
        logger.info("------创建JWT------");

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("user_name", userName)
                .setIssuedAt(now)
                .setAudience(userName)
                .signWith(signatureAlgorithm, signingKey);

        //添加Token过期时间
        long expMillis = nowMillis + expireMills;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(now);

        //生成JWT
        return builder.compact();
    }

    /**
     * 解析JWT
     * @return
     */
    public static Claims parseJWT(Object...param){
        logger.info("------解析JWT------");
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(param[0]+""))
                .parseClaimsJws(param[1]+"").getBody();
        return claims;
    }
}
