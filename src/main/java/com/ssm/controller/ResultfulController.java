package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.common.ResultMsg;
import com.ssm.common.ResultStatusCode;
import com.ssm.model.Student;
import com.ssm.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/")
public class ResultfulController {

    @Resource
    private StudentService studentService;

    @RequestMapping(value = "selectPageStudent",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg selectStu(@RequestParam("pageNum") int pageNum,
                                               @RequestParam("pageSize") int pageSize,
                                               @RequestParam(value = "no",required = false) String no,
                                               @RequestParam(value = "name",required = false) String name,
                                               @RequestParam(value = "sex",required = false) String sex,
                                               @RequestParam(value = "age",required = false) String age){
        ResultMsg resultMsg;
        Student student = new Student();
        student.setPageNum(pageNum);
        student.setPageSize(pageSize);
        student.setNo(no);
        student.setName(name);
        student.setSex(sex);
        if(age!=null && age.trim().length()!=0){
            student.setAge(Integer.parseInt(age));
        }
        PageInfo list = studentService.selectPageStudent(student);
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),list);
        return resultMsg;
    }

    @RequestMapping(value = "insertStudnet",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg insertStudnet(@RequestBody Student student){
        ResultMsg resultMsg;
        student.setNo(student.getNo().trim());
        Student stu = studentService.selectStudentByNo(student.getNo());
        if(stu!=null){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_ALREADY_EXIST.getErrcode(),
                    ResultStatusCode.INVALID_ALREADY_EXIST.getErrmsg(),false);
            return resultMsg;
        }
        int request = studentService.insertStudnet(student);
        if(request>0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

    @RequestMapping(value = "updateStudent",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg updateStudent(@RequestParam("no") String no,
                                   @RequestParam(value = "name",required = false) String name,
                                   @RequestParam(value = "sex",required = false) String sex,
                                   @RequestParam(value = "age",required = false) String age){
        ResultMsg resultMsg;
        Student student = new Student();

        student.setNo(no.trim());
        Student stu = studentService.selectStudentByNo(student.getNo());
        if(stu==null){
            resultMsg = new ResultMsg(ResultStatusCode.INVALID_NOT_FOUND.getErrcode(),
                    ResultStatusCode.INVALID_NOT_FOUND.getErrmsg(),"找不到此编号的学生");
            return resultMsg;
        }
        student.setName(name);
        student.setSex(sex);
        if(age!=null && age.trim().length()>0){
            student.setAge(Integer.parseInt(age));
        }
        int request = studentService.updateStudent(student);
        if(request>0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

    @RequestMapping(value = "deleteStudent/{no}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg deleteStudent(@PathVariable("no") String no){
        ResultMsg resultMsg;
        int request = studentService.deleteStudent(no);
        if(request>0){
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                    ResultStatusCode.OK.getErrmsg(),true);
            return resultMsg;
        }
        resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),
                ResultStatusCode.OK.getErrmsg(),false);
        return resultMsg;
    }

}
