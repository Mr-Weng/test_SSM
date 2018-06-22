package com.ssm.common.token;

import lombok.Data;

/**
 * token返回结果类
 */
@Data
public class AccessToken {
    //token
    private String access_token;
    //类型
    private String token_type;
    //过期
    private long expires_in;
}
