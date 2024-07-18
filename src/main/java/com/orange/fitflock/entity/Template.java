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
import java.time.LocalDateTime;

/**
 * 计划模板表 实体类。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "template")

public class Template implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模板id
     */
    @Id(keyType = KeyType.Auto)
    private Integer templateId;

    /**
     * 模板名字
     */
    private String templateName;

    /**
     * 用户id  官方-1
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 动作id
     */
    private Integer actionId;

    /**
     * 是否公开
     */
    private String isPublic;

}
