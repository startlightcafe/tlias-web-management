package com.itheima.controller;

import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    //分页条件查询
    @GetMapping("/emps")
    public Result selectByPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               String name, Short gender,
                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                               @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询，{}{}{}{}{}{}",page,pageSize,name,gender,begin,end);
        PageBean pageBean = empService.selectByPage(page, pageSize, name, gender, begin, end);
        return Result.success(pageBean);
    }


    //批量删除员工 (<foreach>)
    @DeleteMapping("/emps/{ids}")
    public Result deleteAll(@PathVariable Integer[] ids){ //也可以使用List集合接收
        log.info("批量删除操作，ids: {}", (Object) ids);
        empService.deleteAll(ids);
        return Result.success();
    }


}
