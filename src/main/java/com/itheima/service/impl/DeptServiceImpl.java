package com.itheima.service.impl;

import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.pojo.DeptLog;
import com.itheima.service.DeptLogService;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private DeptLogService deptLogService;


    @Override
    public List<Dept> selectAll() {
        return deptMapper.selectAll();
    }



    @Transactional(rollbackFor = Exception.class) //spring事务管理
    @Override
    public void deleteById(Integer id) throws Exception {
        try {
            deptMapper.deleteById(id);//删除部门信息

            //if(true) throw new Exception("出错了...");
            //int i = 1/0;

            empMapper.deleteByDeptId(id);//删除该部门下的所有员工信息

        } finally {
            //记录操作日志
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作, 解散的是"+id+"号部门");
            deptLogService.insert(deptLog);
        }
    }



    @Override
    public void insert(Dept dept) {
        // 这里要补全补全 dept对象 的其他成员属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

}
