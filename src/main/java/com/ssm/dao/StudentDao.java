package com.ssm.dao;

import com.ssm.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {

    List<Student> selectPageStudent(Student student);

    Student selectStudentByNo(String no);

    int insertStudnet(Student student);
    int updateStudent(Student student);
    int deleteStudent(String no);

}
