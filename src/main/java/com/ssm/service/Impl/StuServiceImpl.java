package com.ssm.service.Impl;

import com.ssm.dao.StuDao;
import com.ssm.model.STU;
import com.ssm.service.StuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuDao stuDao;

    @Override
    public List<STU> selectStu(STU stu) {
        return stuDao.selectStu(stu);
    }

    @Override
    public int selectStuByName(String name) {
        return stuDao.selectStuByName(name);
    }

    @Override
    public int insertAll(STU stu) {
        return stuDao.insertAll(stu);
    }

    @Override
    public int deleteStu(String name) {
        return stuDao.deleteStu(name);
    }

    @Override
    public int updateStu(STU stu) {
        return stuDao.updateStu(stu);
    }
}
