package com.ssm.controller;

import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.tool.SnowflakeIdWorker;
import com.ssm.model.Role;
import com.ssm.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RoleController {

    @Resource
    private RoleService roleService;

    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    /**
     * 新增角色信息（需要权限）
     * @param role
     * @return
     */
    @RequestMapping(value = "protect/insertRole",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg insertRole(@RequestBody Role role){
        ResultMsg resultMsg;
        role.setRname(role.getRname().trim());
        Role role1 = roleService.selectRoleByName(role.getRname());
        if(role1 != null){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ALREADY_EXIST.getErrcode(),
                    ResultStatusCode.INVALID_ALREADY_EXIST.getErrmsg(),"角色名称已存在");
            return resultMsg;
        }

        role.setRid(String.valueOf(snowflakeIdWorker.nextId()));
        int request = roleService.insertRole(role);
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
     * 根据角色id查找单个角色
     * @param rid
     * @return
     */
    @RequestMapping(value = "selectRoleByRid/{rid}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg selectRoleByRid(@PathVariable("rid") String rid){
        ResultMsg resultMsg;
        Role role = roleService.selectRoleByRid(rid);
        if(role != null){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),role);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.INVALID_NOT_FOUND.getErrcode(),
                ResultStatusCode.INVALID_NOT_FOUND.getErrmsg(),false);
        return resultMsg;
    }

    /**
     * 获取所有角色信息
     * @return
     */
    @RequestMapping(value = "selectAllRole",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg selectAllRole(){
        ResultMsg resultMsg;
        List<Role> list = roleService.selectAllRole();
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),list);
        return resultMsg;
    }

}
