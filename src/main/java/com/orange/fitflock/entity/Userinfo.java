package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 用户信息表 实体类。
 *
 * @author g1310
 * @since 2024-02-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "userinfo")

public class Userinfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    private Integer userId;

    /**
     * 头像图片地址
     */
    private String avatarUrl;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Date birthday;

    /**
     * 身高-cm
     */
    private Integer height;

    /**
     * 体重-kg
     */
    private Double weight;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 小组数量
     */
    private Integer groupNum;

    /**
     * 用户展示唯一标识
     */
    private String uuid;

    /**
     * 上一次修改时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;

}
