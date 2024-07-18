package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小程序用户表 实体类。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user")

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 唯一标识
     */
    @Id
    private String openId;

    /**
     * 注册时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;

    /**
     * 会话秘钥
     */
    private String sessionKey;

}
