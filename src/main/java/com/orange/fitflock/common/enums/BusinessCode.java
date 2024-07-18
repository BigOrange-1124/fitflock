package com.orange.fitflock.common.enums;

/**
 * @title: BusinessCode
 * @author: 郭皓隆
 * @date: 2023-09-22
 * @Description: 业务返回码
 */
public enum BusinessCode {
    //操作成功
    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    EMPTY(402, "查询为空"),
    TOKEN_UNAUTHORIZED(401, "Token校验未通过！"),
    TRANSACTION_PASSWORD_NOT_SET(20001, "用户未设置交易密码，请先设置交易密码"),
    LOGIN_CODE_INVALID(100001, "登录出错，请重试"),
    LOGIN_CODE_BLOCKED(100002, "当前账号存在风险，请进行验证后登录"),
    UPLOAD_CODE_INVALID(200001, "上传图片出错，请重试"),

    GROUP_CODE_MAX(3000001, "小组数量已上限"),
    GROUP_CODE_NOT_EXIST(3000002, "小组编号不存在"),
    GROUP_CODE_NOT_NULL(3000003, "小组代码不能为空"),
    GROUP_NAME_EXIST(3000004, "小组名称已存在"),
    GROUP_CODE_FAIL(3000005, "小组创建失败"),
    GROUP_CODE_HAS_EXIST(3000006, "不可重复加小组"),
    GROUP_CODE_GROUP_USER_MAX(3000007, "小组成员已满"),
    GROUP_CODE_REMOVE_FAIL(3000008, "退出小组失败");


    /**
     * 返回码
     */
    private final int code;

    /**
     * 返回信息
     */
    private final String msg;

    /**
     * @param code
     * @param msg
     */
    BusinessCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
