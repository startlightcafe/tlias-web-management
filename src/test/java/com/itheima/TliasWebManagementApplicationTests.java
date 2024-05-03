package com.itheima;

import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import com.itheima.service.DeptService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

//如果需要测试的代码与 spring项目无关, 可以将这个注解删除
@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    public void testUuid(){
        String uuid = UUID.randomUUID().toString();
    }

    //阿里云OSS使用测试
    @Test
    public void testaliyunOSS() throws Exception {
        Demo.aliyunOSS();
    }

    //生成JWT令牌
    @Test
    public void testGetJWT(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","Tom");

        //生成JWT令牌
        String jwt = Jwts.builder()
                //(数字签名的算法, 密钥字符串)
                .signWith(SignatureAlgorithm.HS256, "itheima")
                //(存放自定义内容(载荷)的Map对象)
                .setClaims(claims)
                //(Date对象) 有效期    (下面的代码将设置为 1h)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();

        System.out.println(jwt);
    }

    //解析JWT令牌
    @Test
    public void testParseJWT(){
        Claims claims=Jwts.parser()
                //指定签名密钥, 必须保证和生成令牌时使用相同的签名密钥
                .setSigningKey("itheima")
                //jwt令牌字符串
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9" +
                        ".eyJuYW1lIjoiVG9tIiwiaWQiOjEsImV4cCI6MTcxNDY1NTAyOX0" +
                        ".5qDCcqM0yB0VSMA5aTsfJsE6hlfF7dBNvOjDrUPTwGA")
                .getBody();

        System.out.println(claims); //{name=Tom, id=1, exp=1714655029}
    }
}
