package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    //@Select("select count(*) from emp")
    //public Long count(); //获取总记录数

    //查询并返回一页的数据 (起始索引 , 每页展示的记录数)
    /*

    @Select("select * from emp limit #{start},#{PageSize}")
    public List<Emp> PageList(@Param("start") Integer start, @Param("PageSize") Integer PageSize);
    public List<Emp> PageList(Integer start, Integer PageSize);

    */

    @Select("select * from emp")
    public List<Emp> selectAll();

    //条件查询
    public List<Emp> select(String name, Short gender, LocalDate begin, LocalDate end);

    void deleteAll(Integer[] ids);

    void insert(Emp emp);

    @Select("select * from emp where id = #{id}")
    Emp selectById(Integer id);

    void update(Emp emp);


    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);

    //根据部门 id删除该部门下的所有员工
    @Delete("delete from emp where dept_id = #{deptId}")
    void deleteByDeptId(Integer deptId);
}
