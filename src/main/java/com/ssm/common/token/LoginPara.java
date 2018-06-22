package com.ssm.common.token;

import lombok.Data;

/**
 * token认证信息类
 */
@Data
public class LoginPara {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码 暂时不用
     */
    private String captchaCode;
    /**
     * 验证码值 暂时不用
     */
    private String captchaValue;
}
