package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
    @Log
    @DeleteMapping("/emps/{ids}")
    public Result deleteAll(@PathVariable Integer[] ids){ //也可以使用List集合接收
        log.info("批量删除操作，ids: {}", (Object) ids);
        empService.deleteAll(ids);
        return Result.success();
    }


    //添加员工 (传递过来的是 Json格式的数据, 所以要使用@RequestBody)
    @Log
    @PostMapping("/emps")
    public Result insert(@RequestBody Emp emp){
        log.info("新增员工: {}",emp);
        empService.insert(emp);
        return Result.success();
    }

    //根据 id查询员工
    @GetMapping("/emps/{id}")
    public Result selectById(@PathVariable Integer id){
        log.info("根据id查询员工信息: id={}",id);
        Emp emp = empService.selectById(id);
        return Result.success(emp);
    }

    //修改员工信息 (请求数据是Jsn格式的, 所以要添加注解@RequestBody)
    @Log
    @PutMapping("emps")
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息:{}",emp);
        empService.update(emp);
        return Result.success();
    }
}
