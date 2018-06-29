package com.ssm.dao;

import com.ssm.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {

    Role selectRoleByRid(String rid);//根据角色id查找角色
    Role selectRoleByName(String rname);//根据角色名称查找角色
    List<Role> selectAllRole(); //获取所有角色信息

    int insertRole(Role role);//新增角色

}
