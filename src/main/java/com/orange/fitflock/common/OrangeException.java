package com.orange.fitflock.common;

import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.common.enums.ErrorCode;
import lombok.Data;

/**
 * @program: Orange
 * @Author: 常昊
 * @Date: 2023/09/19/20:25
 * @Description: 服务异常类型
 */
public class OrangeException extends RuntimeException {

    /* 错误编码*/
    private int code;

    /* 错误信息*/
    private String msg;

    /* 外部错误信息 */
    private String logMsg;

    public OrangeException(Throwable cause) {
        super(cause);
    }

    public OrangeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public OrangeException(ErrorCode errorCode) {
        super(errorCode.getErrorCode() + "|" + errorCode.getErrorDesc());
        this.code = errorCode.getErrorCode();
        this.msg = errorCode.getErrorDesc();
    }

    public OrangeException(BusinessCode businessCode) {
        super(businessCode.getMsg());
        this.code = businessCode.getCode();
        this.msg = businessCode.getMsg();
    }

    public OrangeException(int code, String msg) {
        super(code + "|" + msg);
        this.code = code;
        this.msg = msg;
    }

    public OrangeException(int code, String msg, String logMsg) {
        super(code + "|" + msg + "|" + logMsg);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }


    public OrangeException(String message, int code, String msg, String logMsg) {
        super(message);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }

    public OrangeException(String message, Throwable cause, int code, String msg, String logMsg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }

    public OrangeException(Throwable cause, int code, String msg, String logMsg) {
        super(cause);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }

    public OrangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                           int code, String msg, String logMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
        this.logMsg = logMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    @Override
    public String toString() {
        return "ServiceException{"
                + "code=" + code
                + ", msg='" + msg + '\''
                + ", logMsg='" + logMsg + '\''
                + '}';
    }
}
