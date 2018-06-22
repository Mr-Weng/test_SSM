package com.ssm.service;

import com.github.pagehelper.PageInfo;
import com.ssm.model.Student;

public interface StudentService {

    PageInfo selectPageStudent(Student student);

    Student selectStudentByNo(String no);

    int insertStudnet(Student student);
    int updateStudent(Student student);
    int deleteStudent(String no);

}
