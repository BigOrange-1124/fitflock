package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 小组成员表 实体类。
 *
 * @author g1310
 * @since 2024-02-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "group_member")

public class GroupMember implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成员id
     */
    @Id(keyType = KeyType.Auto)
    private Integer memberId;

    /**
     * 小组id
     */
    private String groupId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 加入日期
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime joinTime;

}
