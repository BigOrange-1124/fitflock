package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 用户操作日志表 实体类。
 *
 * @author g1310
 * @since 2024-03-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_log")

public class UserLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @Id(keyType = KeyType.Auto)
    private Integer logId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 发生日期
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime logDatetime;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 型号
     */
    private String model;

    /**
     * 操作系统
     */
    private String system;

    /**
     * 操作平台
     */
    private String platform;

    /**
     * 微信版本
     */
    private String version;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 接口
     */
    @Column(value = "requestURL")
    private String requestURL;

    /**
     * 耗时 ms
     */
    private BigInteger elapsedTime;

}
