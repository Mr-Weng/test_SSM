package com.ssm.controller;

import com.ssm.model.STU;
import com.ssm.service.StuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private StuService stuService;

    @RequestMapping(value = "selectStu",method = RequestMethod.POST)
    public String selectStu(STU stu,Model model){

        List<STU> list = stuService.selectStu(stu);
        model.addAttribute("list",list);
        return "index";

    }

    @RequestMapping(value = "insertAll",method = RequestMethod.POST)
    public String insertAll(STU stu,Model model){
        if(stuService.selectStuByName(stu.getName()) == 0){
            int request = stuService.insertAll(stu);
            if(request>0){
                return "forward:selectStu";
            }
            model.addAttribute("error","插入失败");
            return "forward:selectStu";
        }else{
            model.addAttribute("error","该姓名已存在，请重新输入");
            return "forward:selectStu";
        }
    }

    @RequestMapping(value = "deleteStu",method = RequestMethod.POST)
    public String deleteStu(String name,Model model){
        int request = stuService.deleteStu(name);
        if(request>0){
            model.addAttribute("error","删除成功！");
            return "forward:selectStu";
        }
        model.addAttribute("error","无此姓名，删除失败！");
        return "forward:selectStu";
    }

    @RequestMapping(value = "updateStu",method = RequestMethod.POST)
    public String updateStu(STU stu,Model model){
        int request = stuService.updateStu(stu);
        if(request>0){
            model.addAttribute("error","修改成功！");
            return "forward:selectStu";
        }
        model.addAttribute("error","无此姓名，修改失败！");
        return "forward:selectStu";
    }

}
