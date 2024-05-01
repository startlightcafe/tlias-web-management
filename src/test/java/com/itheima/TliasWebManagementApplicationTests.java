package com.itheima;

import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import com.itheima.service.DeptService;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TliasWebManagementApplicationTests {
    @Autowired
    private DeptService deptService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSelectAll(){
        List<Dept> depts = deptService.selectAll();
        System.out.println(depts);
    }

    @Autowired
    private EmpMapper empMapper;
    @Test
    public void testList() {
//        List<Emp> list = empMapper.select("张", (short) 1, LocalDate.of(2010, 1, 1),
//                LocalDate.of(2020, 1, 1));
        List<Emp> list = empMapper.select( null, (short) 1, null,null);
        list.forEach(System.out::println);

    }
}
