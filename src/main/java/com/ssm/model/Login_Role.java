package com.ssm.model;

import lombok.Data;

@Data
public class Login_Role {

    private String lrid;
    private String lid; //用户id
    private String lname; //用户名称
    private String rid; //角色id
    private String rname; //角色名称

}
