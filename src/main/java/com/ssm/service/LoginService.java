package com.ssm.service;

import com.ssm.model.Login;
import com.ssm.model.Test;

import java.util.List;

public interface LoginService {

    List<Login> selectAllUser(); //获取所有用户
    Login selectUserByID(String id); //根据id查询用户
    Login selectUserByName(String username); //根据用户名查询用户
    int insertLogin(Login login); //添加用户
    int updateLogin(String oldName,Login login); //修改用户（除管理员，只能修改自身）
    int deleteLogin(String userid); //删除用户，需要权限

    List<Test> testAll(String tid); //左连接测试

    /**
     * 事务测试方法
     * @param test
     */
    void test(List<Test> test);

}
