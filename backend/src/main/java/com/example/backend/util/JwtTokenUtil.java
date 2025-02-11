package com.example.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JwtTokenUtil {

    private static final String SECRET_KEY = "your_secret_key";  // 密钥（应保密）

    // 生成 JWT
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)  // 设置主题为用户名
                .withIssuedAt(new Date())  // 设置签发时间
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))  // 设置过期时间为1小时
                .sign(Algorithm.HMAC256(SECRET_KEY));  // 使用 HMAC256 加密算法和密钥签名
    }

    // 从 JWT 中提取用户名
    public static String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
        return decodedJWT.getSubject();  // 获取用户名
    }

    // 公共方法：验证 token 是否过期
    public static boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
        return decodedJWT.getExpiresAt().before(new Date());  // 判断是否过期
    }

    // 验证 JWT 是否有效
    public static boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (username.equals(extractedUsername) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;  // 如果验证失败，返回 false
        }
    }
}
