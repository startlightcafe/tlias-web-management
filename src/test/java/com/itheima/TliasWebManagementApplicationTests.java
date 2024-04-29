package com.itheima;

import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
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

}
