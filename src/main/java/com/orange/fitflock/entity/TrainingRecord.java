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
 * 训练记录表 实体类。
 *
 * @author g1310
 * @since 2024-02-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "training_record")

public class TrainingRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @Id(keyType = KeyType.Auto)
    private Integer recordId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 动作id
     */
    private Integer actionId;

    /**
     * 训练日期
     */
    private LocalDateTime trainingDate;

    /**
     * 训练重量
     */
    private Double weight;

    /**
     * 每组次数
     */
    private Integer repsPerSet;

    /**
     * 有氧持续时间
     */
    private Integer durationMinutes;
//
//    /**
//     * 关联小组1
//     */
//    private String groupId1;
//
//    /**
//     * 关联小组2
//     */
//    private String groupId2;
//
//    /**
//     * 关联小组3
//     */
//    private String groupId3;

    /**
     * 组数顺序id
     */
    private Integer inputId;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;

}
