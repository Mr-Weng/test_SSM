package com.ssm.service;

import com.ssm.model.STU;

import java.util.List;

public interface StuService {

    List<STU> selectStu(STU stu);

    int selectStuByName(String name);

    int insertAll(STU stu);

    int deleteStu(String name);

    int updateStu(STU stu);

}
