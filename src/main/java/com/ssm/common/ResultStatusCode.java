package com.ssm.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 廖志群
 * @version 1.00
 * @date 五月  19 2017,10:20
 * 返回错误代码 不能使用lombok 枚举
 */

public enum ResultStatusCode {


    /**
     * ok
     */
    OK(0, "OK"),

    /**
     * 系统错误
     */
    SYSTEM_ERR(30001, "System error"),
    /**
     * 无效的ClientId
     */
    INVALID_CLIENTID(30003, "Invalid ClientId"),
    /**
     * 用户名或密码不正确
     */
    INVALID_PASSWORD(30004, "User name or password is incorrect"),
    /**
     * 验证码或验证码无效逾期
     */
    INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),
    /**
     * 令牌无效
     */
    INVALID_TOKEN(30006, "Invalid token"),
    /**
     * 查找不到
     */
    INVALID_NOT_FOUND(30007, "Not Found"),

    /**
     * 无权限
     */
    INVALID_PERMISSION(30008,"Invalid Permission"),

    /**
     * 已存在
     */
    INVALID_ALREADY_EXIST(30009,"Already exist");

    //成员变量
    @Setter
    @Getter
    private int errcode;

    @Setter
    @Getter
    private String errmsg;



    private ResultStatusCode(int errCode, String errMsg)
    {
        this.errcode = errCode;
        this.errmsg = errMsg;
    }
}
