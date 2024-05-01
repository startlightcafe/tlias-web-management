package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.pojo.Result;
import com.itheima.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    //文件上传演示

    @Autowired
    private AliOSSUtils aliOSSUtils;

    //如果前端传递的参数名称 与 服务端方法名称不一致, 可以使用:
    //  @RequestParam("前端传递过来的参数名")数据类型 方法形参
    /*@PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws IOException, ClientException {

        //定义MultipartFile 类的对象 image来接收传递过来的文件
        log.info("文件上传: {}{}{}", username,age,image);

        //1.获取原始文件名
        String originalFilename = image.getOriginalFilename(); // 01.jpg

        //2.获取文件扩展名
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index); // .jpg

        //3.生成唯一的文件名 ( uuid )
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid + extname; //拼接
        log.info("新文件名: {}",newFilename);

        //4.将接收到的文件存储在 服务端的磁盘目录中 (E:\my_Code\ITheima\JavaWeb\file\images)
        image.transferTo(new File("E:\\my_Code\\ITheima\\JavaWeb\\file\\images\\" + newFilename));

        return Result.success();
    }*/

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException, ClientException {
        log.info("文件上传: {}", image);

        String url = aliOSSUtils.upload(image);

        log.info("文件上传完成, url为{}", url);
        return Result.success(url);
    }
}
