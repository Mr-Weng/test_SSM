package com.ssm.service;

import com.ssm.model.Jurisdiction;

public interface JurisdictionService {

    Jurisdiction selectJrdByAction(String action); //根据资源动作字符串查找资源
    int insertJurisdiction(Jurisdiction jurisdiction); //新增资源（需要权限）

}
