package com.orange.fitflock.dto;

import lombok.Data;

/**
 * @title: JsCode2sessionDto
 * @author: 郭皓隆
 * @date: 2023-09-25
 * @Description:
 */
@Data
public class JsCode2sessionDto {
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;
}
