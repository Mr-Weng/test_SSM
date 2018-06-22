package com.ssm.controller;

import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.token.AccessToken;
import com.ssm.common.token.Audience;
import com.ssm.common.token.EncryptionHelper;
import com.ssm.common.token.LoginPara;
import com.ssm.common.tool.JwtHelper;
import com.ssm.common.tool.SnowflakeIdWorker;
import com.ssm.model.Login;
import com.ssm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/")
public class LoginController {

    @Resource
    private LoginService loginService;
    @Resource
    private Audience audience;

    //生成ID
    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    /**
     * 登录，获取token
     */
    @RequestMapping(value = "login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg Login(@RequestBody LoginPara loginPara){
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

            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUsername(), user.getUserID(),
                    user.getPassword(), audience.getClientId(), audience.getName(),
                    audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            //返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audience.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(), accessTokenEntity);
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
     * 新增用户（注册）
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
     * 修改用户信息
     * @param login
     * @return
     */
    @RequestMapping(value = "updateLogin",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg updateLogin(@RequestBody Login login){
        ResultMsg resultMsg;
        if(loginService.selectUserByID(login.getUserID())!=null){
            int request = loginService.updateLogin(login);
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
