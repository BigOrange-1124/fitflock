package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 *  实体类。
 *
 * @author g1310
 * @since 2024-02-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "action_capacity")
public class ActionCapacity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 容量ID
     */
    @Id(keyType = KeyType.Auto)
    private Integer capacityId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 动作ID
     */
    private Integer actionId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 容量
     */
    private Double capacity;

    /**
     * 最大重量
     */
    private Double maxWeight;

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
