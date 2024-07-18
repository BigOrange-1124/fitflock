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

/**
 * 实体类。
 *
 * @author g1310
 * @since 2024-02-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "images")

public class Images implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    private String imagesUrl;

    /**
     * 1-轮播图，
     */
    private Integer sort;

    /**
     * 0-弃用，1-使用
     */
    private Integer status;

}
