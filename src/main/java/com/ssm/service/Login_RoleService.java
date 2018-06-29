package com.ssm.service;

import com.ssm.model.Login_Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Login_RoleService {

    Login_Role selectHasRid(String lid,String rid); //查看当前用户是否有当前角色
    List<Login_Role> selectAllByLid(String lid); //根据用户id查找关联角色
    List<Login_Role> selectAllByLName(String username); //根据用户名查找关联角色（登录的接口调用到）

    int insertLR(Login_Role login_role);//新增用户-角色（授权，需要权限）

    int deleteRByLidRid(String lid,String rid); //根据用户id与角色id 删除用户-角色（释权，需要权限）

    int updateRByLidRid(String newRid, String newRname,
                        String lid, String rid); //根据用户id和角色id修改角色信息（改权，需要权限）

}
