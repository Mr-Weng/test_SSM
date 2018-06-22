package com.ssm.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {

    private String no;
    private String name;
    private String sex;
    private int age;

    public int pageNum;
    public int pageSize;

}
