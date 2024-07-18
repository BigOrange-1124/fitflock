package com.orange.fitflock.dto;

import lombok.Data;

/**
 * @title: LoginDto
 * @author: 郭皓隆
 * @date: 2023-09-28
 * @Description: 登录接口dto载体
 */
@Data
public class LoginDto {
    /**
     * 代码
     */
    private String code;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 系统
     */
    private String system;

    /**
     * 平台
     */
    private String platform;

    /**
     * 版本
     */
    private String version;

}
