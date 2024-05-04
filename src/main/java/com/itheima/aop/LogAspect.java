package com.itheima.aop;

import com.alibaba.fastjson.JSONObject;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Autowired //从请求头中获取JWT令牌
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;


    @Around("@annotation(com.itheima.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //1.获取操作人id (获取请求头中的JWT令牌)
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt); //claims 一个map集合
        Integer operateUserId = (Integer) claims.get("id");

        //2.操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //3.操作类名
        String operateClassName = joinPoint.getTarget().getClass().getName();

        //4.操作方法名
        String operateMethodName = joinPoint.getSignature().getName();

        //5.操作方法参数
        Object[] args = joinPoint.getArgs();
        String operateMethodArgs = Arrays.toString(args);

        // 方法运行前的时间
        long beginTime = System.currentTimeMillis();

        //6.调用原始目标方法运行
        Object result = joinPoint.proceed();

        // 方法运行结束的时间
        long endTime = System.currentTimeMillis();

        //7.方法返回值 (转为Json字符串)
        String returnValue = JSONObject.toJSONString(result);

        //8.操作耗时
        long costTime = endTime - beginTime;

        //记录操作日志
        OperateLog operateLog = new OperateLog(null,operateUserId,operateTime,operateClassName,operateMethodName,operateMethodArgs,returnValue,costTime);

        operateLogMapper.insert(operateLog);
        log.info("AOP记录操作日志: {}",operateLog);

        return result; //将原方法的结果返回, 保证其不影响原方法的功能
    }
}
