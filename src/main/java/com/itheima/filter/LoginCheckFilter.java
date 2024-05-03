package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //1.获取请求url
        String url = req.getRequestURL().toString();
        log.info("请求的url为: {}",url);

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("login")){
            log.info("登录操作, 直接放行");
            chain.doFilter(request, response);

            //由于放行之后还会回到这个放行的地方(上方的放行代码之后),
            // 所有这里直接return , 不再执行下面的操作
            return;
        }

        //3.获取请求头中的令牌 (请求头的名字为token, 接口文档有写)
        String jwt = req.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        //  (StringUtils要1导入 springframework 包下的)
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空, 返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");

            //由于controller层加上了@RestController注解, 所以能自动将结果转为Json响应回去,
            // 但是在这里我们需要手动将其转为Json (可在引入依赖后使用阿里巴巴fastJson工具包)
            String noLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(noLogin);

            return;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("JWT令牌解析失败, 返回未登录的错误信息");

            Result error = Result.error("NOT_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(noLogin);
            return;
        }

        //6.放行
        log.info("令牌合法, 放行");
        chain.doFilter(request, response);
    }
}
