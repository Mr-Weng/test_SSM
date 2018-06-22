package com.ssm.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.dao.StudentDao;
import com.ssm.model.Student;
import com.ssm.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public PageInfo selectPageStudent(Student student) {
        PageHelper.startPage(student.getPageNum(),student.getPageSize());
        List<Student> list = studentDao.selectPageStudent(student);
        PageInfo page = new PageInfo(list);
        return page;
    }

    @Override
    public Student selectStudentByNo(String no) {
        return studentDao.selectStudentByNo(no);
    }

    @Override
    public int insertStudnet(Student student) {
        return studentDao.insertStudnet(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    @Override
    public int deleteStudent(String no) {
        return studentDao.deleteStudent(no);
    }
}
