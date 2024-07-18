package com.orange.fitflock.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;

/**
 * 动作表 实体类。
 *
 * @author g1310
 * @since 2024-02-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "action")

public class Action implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 动作id
     */
    @Id(keyType = KeyType.Auto)
    private Integer actionId;

    /**
     * 动作名称
     */
    private String actionName;

    /**
     * 动作描述
     */
    private String actionDescription;

    /**
     * 1-杠铃2-哑铃3-拉索4-固定器械5-自重
     */
    private Integer actionTypeId;

    /**
     * 动作图片
     */
    private String actionPicture;

    /**
     * 1-胸，2-肩，3-背，4-腿，5-二头，6-三头，7-腹，8-有氧
     */
    private Integer partId;

}
