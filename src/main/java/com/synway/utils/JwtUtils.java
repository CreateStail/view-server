package com.synway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.synway.domain.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {
    private static final String SUBJECT = "mySign";
    private static final long EXPIRE = 1000 * 60 * 60 ;//过期时间为1小时
    private static final String APPSECRET = Aesssss.encrypt("myAppSecret",Aesssss.key);

    /**
     * 生成jwt
     */
    public static String getJsonWebToken(User user){
        if(user == null||user.getId()==null||user.getName()==null){
            return null;
        }else{
/*            String token = Jwts.builder().setSubject(SUBJECT)
                            .claim("id",user.getId())
                            .claim("name",user.getName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                            .signWith(SignatureAlgorithm.HS256,APPSECRET)
                            .compact();
            return token;*/
            try {
                String token = JWT.create()
                                .withSubject(SUBJECT)
                                .withClaim("id",user.getId())
                                .withClaim("name",user.getName())
                                .withIssuedAt(new Date())
                                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE))
                                .sign(Algorithm.HMAC256(APPSECRET));
                return token;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
/*    public static Claims checkJsonWebToken(String token){
        try {
            Claims claims = Jwts.parser()
                            .setSigningKey(APPSECRET)
                            .parseClaimsJws(token)
                            .getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }*/
    public static boolean verify(String token,String name,String id){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(token))
                                    .withClaim("id",id)
                                    .withClaim("name",name)
                                    .build();
            verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @param token the token
     * @return token中包含的用户名 username
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("name").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     *
     * @param token the token
     * @return token中包含的用户名 username
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}