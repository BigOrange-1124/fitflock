package com.orange.fitflock.config;

import com.orange.fitflock.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @title: RedisConfig
 * @author: 郭皓隆
 * @date: 2023-08-16
 * @Description: Redis配置类
 */
@Configuration
public class RedisConfig {

    /**
     * 依赖注入
     *
     * @param redisTemplate
     */
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }
}
