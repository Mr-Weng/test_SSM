package com.ssm.controller;

import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.tool.SnowflakeIdWorker;
import com.ssm.model.Login;
import com.ssm.model.Login_Role;
import com.ssm.model.Role;
import com.ssm.service.LoginService;
import com.ssm.service.Login_RoleService;
import com.ssm.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class Login_RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private LoginService loginService;
    @Resource
    private Login_RoleService login_roleService;

    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    /**
     * 新增用户-角色（授权，需要权限）
     * @param lid
     * @param rid
     * @return
     */
    @RequestMapping(value = "protect/insertLR",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg insertLR(@RequestParam("lid") String lid,
                              @RequestParam("rid") String rid){
        ResultMsg resultMsg;
        //根据角色id查询角色
        Role role = roleService.selectRoleByRid(rid);
        if(role != null){ //角色存在
            Login_Role login_role1 = login_roleService.selectHasRid(lid,rid); //查询当前用户是否已有当前角色
            if(login_role1 != null){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_ALREADY_EXIST.getErrcode(),
                        ResultStatusCode.INVALID_ALREADY_EXIST.getErrmsg(),"该用户已有此角色");
                return resultMsg;
            }
            Login login = loginService.selectUserByID(lid); //根据用户id查找用户
            Login_Role login_role = new Login_Role();
            login_role.setLrid(String.valueOf(snowflakeIdWorker.nextId()));
            login_role.setLid(lid);
            login_role.setLname(login.getUsername());
            login_role.setRid(role.getRid());
            login_role.setRname(role.getRname());
            int request = login_roleService.insertLR(login_role); //新增用户-角色
            if(request>0){
                resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                        ResultStatusCode.OK.getErrmsg(),true);
                return resultMsg;
            }else {
                resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                        ResultStatusCode.OK.getErrmsg(),false);
                return resultMsg;
            }
        }else{
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_NOT_FOUND.getErrcode(),
                    ResultStatusCode.INVALID_NOT_FOUND.getErrmsg(),"角色名称不存在");
            return resultMsg;
        }
    }

    /**
     * 根据用户id查找关联角色
     * @param lid
     * @return
     */
    @RequestMapping(value = "selectAllByLid/{lid}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg selectAllByLid(@PathVariable("lid") String lid){
        ResultMsg resultMsg;
        List<Login_Role> list = login_roleService.selectAllByLid(lid);
        if(list != null){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),list);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.INVALID_NOT_FOUND.getErrcode(),
                ResultStatusCode.INVALID_NOT_FOUND.getErrmsg(),false);
        return resultMsg;
    }

    /**
     * 根据用户id与角色id 删除用户-角色（释权，需要权限）
     * @param lid
     * @param rid
     * @return
     */
    @RequestMapping(value = "protect/deleteRByLidRid",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg deleteRByLidRid(@RequestParam("lid") String lid,
                                     @RequestParam("rid") String rid){
        ResultMsg resultMsg;
        int request = login_roleService.deleteRByLidRid(lid, rid);
        if(request>0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

    /**
     * 根据用户id和角色id修改角色信息（改权，需要权限）
     * @param newRid
     * @param lid
     * @param rid
     * @return
     */
    @RequestMapping(value = "protect/updateRByLidRid",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg updateRByLidRid(@RequestParam("newRid") String newRid,
                                     @RequestParam("lid") String lid,
                                     @RequestParam("rid") String rid){
        ResultMsg resultMsg;
        Role role = roleService.selectRoleByRid(newRid);
        if(role != null){
            int request = login_roleService.updateRByLidRid(newRid, role.getRname(), lid, rid);
            if(request>0){
                resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                        ResultStatusCode.OK.getErrmsg(),true);
                return resultMsg;
            }
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),false);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.INVALID_NOT_FOUND.getErrcode(),
                ResultStatusCode.INVALID_NOT_FOUND.getErrmsg(),false);
        return resultMsg;
    }

}
