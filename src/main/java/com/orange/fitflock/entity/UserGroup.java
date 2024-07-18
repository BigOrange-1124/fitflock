package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 用户小组表 实体类。
 *
 * @author g1310
 * @since 2024-02-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_group")

public class UserGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 小组id
     */
    @Id
    private String groupId;

    /**
     * 小组名称
     */
    private String groupName;

    /**
     * 创建者id
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 成员数量
     */
    private Integer memberCount;

}
