package org.springframework.boot.peach.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Date;

@Aspect
@Component
public class LogInterceptor {

    ThreadLocal<Date> beginTime = new ThreadLocal<>();

    Class clazz =null;

    // 定义一个注解切入点
    @Pointcut("@annotation(log)")
    public void logMethodTime(Log log) {
    }

    // 声明切入点的方法执行前，打印开始时间
    @Before("logMethodTime(log)")
    public void beforeLog(JoinPoint joinPoint, Log log) {
        beginTime.set(new Date(System.currentTimeMillis()));
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        clazz = method.getDeclaringClass();
        if (log != null) {
            Logger logger = LoggerFactory.getLogger(clazz);
            logger.info("Method:[" + method.getName() + "] Begin,log level is " + log.level());
        }
    }

    // 声明切入点的方法执行后，打印结束时间和执行耗时
    @After("logMethodTime(log)")
    public void afterLog(JoinPoint joinPoint, Log log) {
        long interval = System.currentTimeMillis() - beginTime.get().getTime();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (log != null) {
            Logger logger = LoggerFactory.getLogger(clazz);
            logger.info("Method:[" + method.getName() + "] End,log level is " + log.level() + " and Total execute time is " + interval + " ms.");
        }
    }
}
