package com.ssm.service.Impl;

import com.ssm.dao.Login_RoleDao;
import com.ssm.model.Login_Role;
import com.ssm.service.Login_RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class Login_RoleServiceImpl implements Login_RoleService {

    @Resource
    private Login_RoleDao login_roleDao;

    @Override
    public Login_Role selectHasRid(String lid, String rid) {
        return login_roleDao.selectHasRid(lid,rid);
    }

    @Override
    public List<Login_Role> selectAllByLid(String lid) {
        return login_roleDao.selectAllByLid(lid);
    }

    @Override
    public List<Login_Role> selectAllByLName(String username) {
        return login_roleDao.selectAllByLName(username);
    }

    @Override
    public int insertLR(Login_Role login_role) {
        return login_roleDao.insertLR(login_role);
    }

    @Override
    public int deleteRByLidRid(String lid, String rid) {
        return login_roleDao.deleteRByLidRid(lid, rid);
    }

    @Override
    public int updateRByLidRid(String newRid, String newRname, String lid, String rid) {
        return login_roleDao.updateRByLidRid(newRid, newRname, lid, rid);
    }
}
