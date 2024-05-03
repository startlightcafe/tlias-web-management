package com.itheima.service;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    PageBean selectByPage(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end);

    void deleteAll(Integer[] ids);

    void insert(Emp emp);

    Emp selectById(Integer id);

    void update(Emp emp);


    Emp login(Emp emp);
}
