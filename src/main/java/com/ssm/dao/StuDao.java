package com.ssm.dao;

import com.ssm.model.STU;

import java.util.List;

public interface StuDao {

    List<STU> selectStu(STU stu);

    int selectStuByName(String name);

    int insertAll(STU stu);

    int deleteStu(String name);

    int updateStu(STU stu);

}
