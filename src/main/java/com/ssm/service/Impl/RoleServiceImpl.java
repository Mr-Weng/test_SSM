package com.ssm.service.Impl;

import com.ssm.dao.RoleDao;
import com.ssm.model.Role;
import com.ssm.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public Role selectRoleByRid(String rid) {
        return roleDao.selectRoleByRid(rid);
    }

    @Override
    public Role selectRoleByName(String rname) {
        return roleDao.selectRoleByName(rname);
    }

    @Override
    public List<Role> selectAllRole() {
        return roleDao.selectAllRole();
    }

    @Override
    public int insertRole(Role role) {
        return roleDao.insertRole(role);
    }
}
