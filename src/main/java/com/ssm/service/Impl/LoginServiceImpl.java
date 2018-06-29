package com.ssm.service.Impl;

import com.ssm.dao.LoginDao;
import com.ssm.dao.Login_RoleDao;
import com.ssm.model.Login;
import com.ssm.model.Login_Role;
import com.ssm.model.Test;
import com.ssm.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;
    @Resource
    private Login_RoleDao login_roleDao;

    @Override
    public List<Login> selectAllUser() {
        return loginDao.selectAllUser();
    }

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

    /**
     * 修改用户（除管理员外，只能修改自身，可以在前端页面将用户id写死）
     * 如果修改了用户名，则需要连同 “用户--角色关联表” 中的用户名一并修改，同样需要用到事务
     * @param oldName：旧的用户名
     * @param login
     * @return
     */
    @Override
    public int updateLogin(String oldName,Login login) {
        if(!oldName.equals(login.getUsername())){//如果用户名不相同，说明修改了用户名，需要连同 "用户--角色关联表" 中的用户名一并修改,同样需要用到事务
            int request1 = loginDao.updateLogin(login);
            int request2 = login_roleDao.updateLRLNameByLid(login.getUsername(),login.getUserID());
            if(request1>0 && request2>0)
                return 1;
            else
                //throw new RuntimeException();
                return -2; //-2,说明为自己定义的错误。
        }else{ //用户名相同，说明只有密码不同，则直接修改。
            return loginDao.updateLogin(login);
        }
    }

    /**
     * 删除用户，需要权限，同时需要事务处理
     * 删除用户前应检查是否与角色关联，如关联，则应该连同“用户-角色关联表”一同删除
     * @param userid
     * @return
     */
    @Override
    public int deleteLogin(String userid) {
        List<Login_Role> list = login_roleDao.selectAllByLid(userid); //根据用户id查找角色关联
        if(list.size()>0){ //若角色关联表存在记录，则应当连同关联表一并删除
            int request1 = login_roleDao.deleteLRAll(userid);
            int request2 = loginDao.deleteLogin(userid);
            if(request1>0 && request2>0)
                return 1;
            else
                return -2;//-2,说明为自己定义的错误。
                //throw new RuntimeException();
        }else {
            return loginDao.deleteLogin(userid);
        }
    }

    @Override
    public List<Test> testAll(String tid) {
        return loginDao.testAll(tid);
    }


    /**
     * 事务测试方法
     * @param test
     */
    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void test(List<Test> test) {
        for(int i=0;i<test.size();i++){
            if(i<2){
                loginDao.test(test.get(i));
            }else{
                throw new RuntimeException();
            }
        }
    }
}
