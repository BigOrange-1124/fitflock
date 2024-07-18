package com.orange.fitflock.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @program: Orange
 * @Author: 常昊
 * @Date: 2023/09/19/20:27
 * @Description: 常见异常信息
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(1001, "参数缺失"),
    /**
     * 算数异常
     */
    ARITHMETIC_EXCEPTION(1002, "算数异常"),
    /**
     * 内部异常
     */
    TRANS_EXCEPTION(1003, "内部异常"),

    // token 异常类

    Token_INVALID(1011, "Token 无效"),

    TOKEN_LOSE(1012, "Token 过期(失效)"),

    Token_ACCOUNT_NULL(1013, "Token ---- 账号为空");


    private Integer errorCode;

    private String errorDesc;

}
