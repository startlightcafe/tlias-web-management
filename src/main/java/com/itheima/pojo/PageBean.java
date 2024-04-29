package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    //用来封装分页查询得到的结果

    private Long total; //总记录数 (数据列表rows的大小)
    private List rows; //当前页的数据列表
}
