package com.orange.fitflock.common;

import com.orange.fitflock.common.enums.BusinessCode;
import lombok.Data;

/**
 * @title: Result
 * @author: 郭皓隆
 * @date: 2023-08-08
 * @Description: 统一返回
 */
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(BusinessCode.SUCCESS.getCode(), BusinessCode.SUCCESS.getMsg());
    }

    public static Result success(Object data) {
        return new Result(BusinessCode.SUCCESS.getCode(), BusinessCode.SUCCESS.getMsg(), data);
    }

    public static Result success(String msg, Object data) {
        return new Result(BusinessCode.SUCCESS.getCode(), msg, data);
    }

    public static Result fail() {
        return new Result(BusinessCode.FAIL.getCode(), BusinessCode.FAIL.getMsg());
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result fail(BusinessCode businessCode) {
        return new Result(businessCode.getCode(), businessCode.getMsg());
    }

}
