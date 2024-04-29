package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@Slf4j //写上这个注解就不用定义日志记录对象了
@RestController
public class DeptController {

    //定义日志记录对象
    //private static Logger log = (Logger) LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    @RequestMapping("/depts")
    public Result selectAll(){
        log.info("查询全部部门数据");

        List<Dept> depts = deptService.selectAll();
        System.out.println(depts);

        return Result.success(depts);
    }
}
