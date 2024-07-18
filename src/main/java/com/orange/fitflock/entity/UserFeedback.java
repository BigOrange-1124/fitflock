package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 用户反馈表 实体类。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "user_feedback")

public class UserFeedback implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer feedbackId;

    private Integer userId;

    private LocalDateTime feedbackDate;

    private Timestamp timestamp;

    private String feedbackType;

    private String feedbackContent;

}
