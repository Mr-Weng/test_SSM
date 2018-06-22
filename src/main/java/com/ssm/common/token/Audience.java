package com.ssm.common.token;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author 廖志群
 * @version 1.00
 * @date 五月  19 2017,13:39
 * 令牌读取配置类
 */
@Configuration
@Setter
@Getter
public class Audience {

    /**
     * 客户端Id
     */
    @Value("#{configProperties['clientId']}")
    private String clientId;

    /**
     *加密钥匙
     */
    @Value("#{configProperties['base64Secret']}")
    public String base64Secret;

    /**
     * 名字
     */
    @Value("#{configProperties['name']}")
    private String name;

    /**
     * 过期时间
     */
    @Value("#{configProperties['expiresSecond']}")
    private int expiresSecond;

//    /**
//     * apiKey
//     */
//    @Value("#{configProperties['apiKey']}")
//    private String apiKey;
//
//    /**
//     *
//     * 图片上传路径
//     */
//    @Value("#{configProperties['imgUrl']}")
//    private String imgUrl ;
//
//    /**
//     *
//     * 视频上传路径
//     */
//    @Value("#{configProperties['videoUrl']}")
//    private String videoUrl ;

}
