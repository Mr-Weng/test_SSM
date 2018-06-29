package com.ssm.dao;

import com.ssm.model.Login_Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Login_RoleDao {

    Login_Role selectHasRid(@Param("lid") String lid,@Param("rid") String rid); //查看当前用户是否有当前角色
    List<Login_Role> selectAllByLid(String lid); //根据用户id查找关联角色
    List<Login_Role> selectAllByLName(String username); //根据用户名查找关联角色

    int updateLRLNameByLid(@Param("lname") String lname,@Param("lid") String lid); //根据用户id修改关联表的用户名

    int insertLR(Login_Role login_role); //新增用户-角色（授权，需要权限）

    int deleteRByLidRid(@Param("lid") String lid,@Param("rid") String rid); //根据用户id与角色id 删除用户-角色（释权，需要权限）
    int deleteLRAll(String userid); //根据用户id删除所有角色关联（删除用户的接口调用到）

    int updateRByLidRid(@Param("newRid") String newRid, @Param("newRname") String newRname,
                        @Param("lid") String lid, @Param("rid") String rid); //根据用户id和角色id修改角色信息（改权，需要权限）

}
