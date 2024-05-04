package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> selectAll();

    void deleteById(Integer id) throws Exception;

    void insert(Dept dept);
}
