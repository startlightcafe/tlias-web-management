package com.itheima.config;

import com.itheima.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //当前类是配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        //指定拦截器拦截的资源 与不需要拦截的资源
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**");
    }
}
