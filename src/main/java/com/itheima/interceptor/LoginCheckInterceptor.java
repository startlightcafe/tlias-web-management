package com.itheima.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override //目标资源方法(controller中的方法) 执行前执行，放回true:放行，返回false:不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求url
        String url = request.getRequestURL().toString();
        log.info("请求的url为: {}",url);

        //2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("login")){
            log.info("登录操作, 直接放行");
            return true;
        }

        //3.获取请求头中的令牌 (请求头的名字为token, 接口文档有写)
        String jwt = request.getHeader("token");

        //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        //  (StringUtils要1导入 springframework 包下的)
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空, 返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");

            //由于controller层加上了@RestController注解, 所以能自动将结果转为Json响应回去,
            // 但是在这里我们需要手动将其转为Json (可在引入依赖后使用阿里巴巴fastJson工具包)
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);

            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("JWT令牌解析失败, 返回未登录的错误信息");

            Result error = Result.error("NOT_LOGIN");
            String noLogin = JSONObject.toJSONString(error);
            response.getWriter().write(noLogin);
            return false;
        }

        //6.放行
        log.info("令牌合法, 放行");
        return true;
    }

    @Override //目标资源方法(controller中的方法) 执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle方法运行了...");
    }

    @Override //视图渲染完毕后执行，最后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion方法运行了...");
    }
}
