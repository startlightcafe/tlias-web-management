package com.itheima.filter;

import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.crypto.Cipher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


//@WebFilter(urlPatterns = "/*") //拦截哪些请求
public class DemoFilter implements Filter {// 注意要导入 javax.servlet包下的

    //初始化方法，Web服务器启动，创建Filter时调用，只调用一次 (可以不写)
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
        System.out.println("init 初始化方法执行了");
    }

    //拦截到请求时,调用该方法，可调用多次 (必须重写)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("DemoFilter 拦截到了请求");

        System.out.println("DemoFilter 放行前逻辑");
        chain.doFilter(request, response); //放行
        System.out.println("DemoFilter 放行后逻辑");
    }

    //销毁方法，服务器关闭时调用，只调用一次 (可以不写)
    @Override
    public void destroy() {
        //Filter.super.destroy();
        System.out.println("destroy 销毁方法执行了");
    }
}
