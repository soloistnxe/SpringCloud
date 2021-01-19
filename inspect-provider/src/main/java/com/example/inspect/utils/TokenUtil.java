package com.example.inspect.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @author soloist
 * @version 1.0
 * @describe
 * @date 2021/1/9 17:18
 */

public class TokenUtil {
    private static String creatToken(Integer userId,String password){
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(userId.toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(password));
        return token;
    }

    public static String getToken(Integer userId,String password){
        return creatToken(userId,password);
    }
}
