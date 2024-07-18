package com.orange.fitflock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @title: WebConfig
 * @author: 郭皓隆
 * @date: 2023-08-11
 * @Description: 配置拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                //需要拦截的路径
                .addPathPatterns("/userinfo/***", "/trainingRecord/***", "/userGroup/***","/user/checkToken")
                //不需要拦截的路径
                .excludePathPatterns("/user/login", "/action/list", "/images/getBackground");
    }

}
