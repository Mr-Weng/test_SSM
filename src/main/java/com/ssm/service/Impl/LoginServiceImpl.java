package com.ssm.service.Impl;

import com.ssm.dao.LoginDao;
import com.ssm.model.Login;
import com.ssm.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    @Override
    public Login selectUserByID(String id) {
        return loginDao.selectUserByID(id);
    }

    @Override
    public Login selectUserByName(String username) {
        return loginDao.selectUserByName(username);
    }

    @Override
    public int insertLogin(Login login) {
        return loginDao.insertLogin(login);
    }

    @Override
    public int updateLogin(Login login) {
        return loginDao.updateLogin(login);
    }
}
