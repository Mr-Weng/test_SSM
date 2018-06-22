package com.ssm.service;

import com.ssm.model.Login;

public interface LoginService {

    Login selectUserByID(String id);
    Login selectUserByName(String username);
    int insertLogin(Login login);
    int updateLogin(Login login);

}
