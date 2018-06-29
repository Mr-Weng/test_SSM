package com.ssm.service.Impl;

import com.ssm.dao.JurisdictionDao;
import com.ssm.model.Jurisdiction;
import com.ssm.service.JurisdictionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class JurisdictionServiceImpl implements JurisdictionService {

    @Resource
    private JurisdictionDao jurisdictionDao;

    @Override
    public Jurisdiction selectJrdByAction(String action) {
        return jurisdictionDao.selectJrdByAction(action);
    }

    @Override
    public int insertJurisdiction(Jurisdiction jurisdiction) {
        return jurisdictionDao.insertJurisdiction(jurisdiction);
    }
}
