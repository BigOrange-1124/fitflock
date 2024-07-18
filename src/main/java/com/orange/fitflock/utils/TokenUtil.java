package com.orange.fitflock.utils;

import cn.hutool.core.util.IdUtil;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @title: Token
 * @author: 郭皓隆
 * @date: 2023-08-10
 * @Description: redis服务类
 */
public final class TokenUtil {

    /**
     * 设置为一天的过期时间，单位为小时
     */
    private static final long TOKEN_EXPIRATION_HOURS = 2;

    /**
     * 生成并存储Token，返回生成的Token
     *
     * @param value openid
     * @return 生成的Token
     */
    public static String generateAndStoreToken(String value) {
        String token = IdUtil.simpleUUID();
        // 存储用户ID到Redis中，并设置过期时间为 TOKEN_EXPIRATION_HOURS 小时
        RedisUtil.setEx("token:" + token, value, TOKEN_EXPIRATION_HOURS, TimeUnit.HOURS);
        return token;
    }

    public static int getUserId(String token) {
        String s = RedisUtil.get("token:" + token);
        return Integer.parseInt(s);
    }

    public static boolean checkToken(String token) {
        return RedisUtil.hasKey("token:" + token);
    }

    /**
     * 续费token
     *
     * @param token
     */
    public static void renewToken(String token) {
        // 延长Token过期时间为 TOKEN_EXPIRATION_HOURS 小时
        RedisUtil.expire("token:" + token, TOKEN_EXPIRATION_HOURS, TimeUnit.HOURS);
    }

    public static void tokenRemoveAll() {
        Set<String> keysToDelete = RedisUtil.keys("token:*");
        // 遍历并删除这些键
        for (String key : keysToDelete) {
            RedisUtil.delete(key);
        }
    }
}

