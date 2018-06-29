package com.ssm.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.token.Audience;
import com.ssm.common.token.EncryptionHelper;
import com.ssm.common.token.LoginPara;
import com.ssm.common.tool.JwtHelper;
import com.ssm.common.tool.SnowflakeIdWorker;
import com.ssm.model.Login;
import com.ssm.model.Login_Role;
import com.ssm.model.Test;
import com.ssm.service.LoginService;
import com.ssm.service.Login_RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/")
public class LoginController {

    @Resource
    private LoginService loginService;
    @Resource
    private Audience audience;
    @Resource
    private Login_RoleService loginRoleService;

    //生成ID
    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    /**********************************************************************************************************************
     * 事务测试方法，可无视
     * @param test
     * @return
     */
    @RequestMapping(value = "test",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg test(@RequestBody Test test){
        ResultMsg resultMsg;
        List<Test> users = new ArrayList<Test>();
        for(int i=0;i<5;i++){
            Test tests = new Test();
            tests.setTid(test.getTid()+i);
            tests.setTname(test.getTname()+"--测试:"+i);
            users.add(tests);
        }

        loginService.test(users);

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),true);
        return resultMsg;
    }

    /**
     * 联合查询
     * @param tid
     * @return
     */
    @RequestMapping(value = "testAll",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg testAll(@RequestParam("tid") String tid){
        ResultMsg resultMsg;
        List<Test> test = loginService.testAll(tid);

        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),test);
        return resultMsg;
    }
    /**********************************************************************************************************************/

    /**
     * 登录，获取token（不被拦截）
     */
    @RequestMapping(value = "login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg Login(HttpServletRequest request, @RequestBody LoginPara loginPara){
        ResultMsg resultMsg;
        try
        {
            if(loginPara.getClientId() == null
                    || (loginPara.getClientId().compareTo(audience.getClientId()) != 0))
            {
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(),
                        ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
                return resultMsg;
            }

            //验证码校验在后面章节添加


            //验证用户名密码
            Login user = loginService.selectUserByName(loginPara.getUsername());
            if (user == null)
            {
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                        ResultStatusCode.INVALID_PASSWORD.getErrmsg(), "用户为空");
                return resultMsg;
            }
            else
            {
                String md5Password = EncryptionHelper.getMD5(loginPara.getPassword()+user.getUserID());

                if (md5Password.compareTo(user.getPassword()) != 0)
                {
                    resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                            ResultStatusCode.INVALID_PASSWORD.getErrmsg(), "密码错误");
                    return resultMsg;
                }
            }

            request.getSession().setAttribute("username",user.getUsername());

            //拼装accessToken
            //权限拦截时，密码参数那块原先为角色参数，可将其改为角色。后在拦截器中解析token时可以判断其是否有权限访问
            String accessToken = JwtHelper.createJWT(loginPara.getUsername(), user.getUserID(),
                    user.getPassword(), audience.getClientId(), audience.getName(),
                    audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            List<Login_Role> list = loginRoleService.selectAllByLName(user.getUsername()); ////根据用户名查找关联角色（登录的接口调用到）

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", accessToken);
            if(list.size()!=0){
                jsonObject.put("userId",list.get(0).getLid());
                for(int i=0;i<list.size();i++){
                    jsonObject.put("roleId"+(i+1),list.get(i).getRid());
                    jsonObject.put("roleName"+(i+1),list.get(i).getRname());
                }
            }

            //返回accessToken
//            AccessToken accessTokenEntity = new AccessToken();
//            accessTokenEntity.setAccess_token(accessToken);
//            accessTokenEntity.setExpires_in(audience.getExpiresSecond());
//            accessTokenEntity.setToken_type("bearer");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(), jsonObject);
            return resultMsg;

        }
        catch(Exception ex)
        {
            resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(),
                    ResultStatusCode.SYSTEM_ERR.getErrmsg(), null);
            return resultMsg;
        }
    }

    /**
     * 新增用户（注册，免token，不被拦截）
     * @param login
     * @return
     */
    @RequestMapping(value = "insertLogin",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg insertLogin(@RequestBody Login login){
        ResultMsg resultMsg;

        login.setUserID(String.valueOf(snowflakeIdWorker.nextId()));
        login.setUsername(login.getUsername().trim());
        Login login1 = loginService.selectUserByName(login.getUsername());
        if(login1!=null){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ALREADY_EXIST.getErrcode(),
                    ResultStatusCode.INVALID_ALREADY_EXIST.getErrmsg(),"用户名已存在");
            return resultMsg;
        }
        //使用 MD5 加密 密码
        login.setPassword(EncryptionHelper.getMD5(login.getPassword()+login.getUserID()));

        int request = loginService.insertLogin(login);
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
     * 修改用户信息(除管理员，只能修改自身，如有修改用户名，需连同用户--角色表一并修改)
     * @param login
     * @return
     */
    @RequestMapping(value = "updateLogin",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg updateLogin(@RequestBody Login login){
        ResultMsg resultMsg;
        login.setUserID(login.getUserID().trim());
        Login login1 = loginService.selectUserByID(login.getUserID());
        if(login1 != null){
            login.setUsername(login.getUsername().trim());
            login.setPassword(EncryptionHelper.getMD5(login.getPassword().trim()+login.getUserID()));
            int request = loginService.updateLogin(login1.getUsername(),login); //修改，多传一个参数是为了在service层作判断
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

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping(value = "selectAllUser",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg selectAllUser(){
        ResultMsg resultMsg;
        List<Login> list = loginService.selectAllUser(); //获取所有用户
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),list);
        return resultMsg;
    }

    /**
     * 删除用户，需要权限，同时需要事务处理
     * 删除用户前应检查是否与角色关联，如关联，则应该连同“用户-角色关联表”一同删除
     * @param userid
     * @return
     */
    @RequestMapping(value = "protect/deleteLogin/{userid}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg deleteLogin(@PathVariable("userid") String userid){
        ResultMsg resultMsg;
        int request = loginService.deleteLogin(userid);
        if(request>0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

}
