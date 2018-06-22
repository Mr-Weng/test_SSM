package com.ssm.dao;

import com.ssm.model.Login;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {

    Login selectUserByID(String id);
    Login selectUserByName(String username);
    int insertLogin(Login login);
    int updateLogin(Login login);

}
