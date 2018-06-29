package com.ssm.controller;

import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.common.tool.SnowflakeIdWorker;
import com.ssm.model.Jurisdiction;
import com.ssm.service.JurisdictionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/")
public class JurisdictionController {

    @Resource
    private JurisdictionService jurisdictionService;

    SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0,0);

    /**
     * 新增资源权限（需要权限）
     * @param jurisdiction
     * @return
     */
    @RequestMapping(value = "protect/insertJurisdiction",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg insertJurisdiction(@RequestBody Jurisdiction jurisdiction){
        ResultMsg resultMsg;

        jurisdiction.setAction(jurisdiction.getAction().trim());
        Jurisdiction jurisdiction1 = jurisdictionService.selectJrdByAction(jurisdiction.getAction());
        if(jurisdiction1 != null){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ALREADY_EXIST.getErrcode(),
                    ResultStatusCode.INVALID_ALREADY_EXIST.getErrmsg(),"资源动作字符串已存在");
            return resultMsg;
        }
        jurisdiction.setJid(String.valueOf(snowflakeIdWorker.nextId()));
        jurisdiction.setJname(jurisdiction.getJname().trim());
        jurisdiction.setJname_info(jurisdiction.getJname_info().trim());
        jurisdiction.setUrl(jurisdiction.getUrl().trim());
        jurisdiction.setMethod(jurisdiction.getMethod().trim());
        jurisdiction.setAction_info(jurisdiction.getAction_info().trim());

        int request = jurisdictionService.insertJurisdiction(jurisdiction);
        if(request > 0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

}
