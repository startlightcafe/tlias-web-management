package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

//原始分页
//    @Override
//    public PageBean selectByPage(Integer page, Integer pageSize) {
//        //1.获取总记录数
//        Long count = empMapper.count();
//        //2.获取分页查询的结果列表
//        int startIndex = (page - 1) * pageSize;
//        List<Emp> empList = empMapper.PageList(startIndex, pageSize);
//        //3.封装成PageBean对象
//        PageBean pageBean = new PageBean(count, empList);
//        return pageBean;
//    }

    @Override
    public PageBean selectByPage(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询, 并将结果进行强转
        List<Emp> empList = empMapper.select(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;

        //3.封装成PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void deleteAll(Integer[] ids) {
        empMapper.deleteAll(ids);
    }

}
