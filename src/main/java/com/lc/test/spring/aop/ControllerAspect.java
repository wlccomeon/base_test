package com.lc.test.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerAspect {

    @Pointcut(value = "execution(* com.lc.test.spring.controller..*.*(..))")
    public void pointCutTest(){};

    @Before(value = "pointCutTest()")
    public void before(JoinPoint joinPoint){
        System.out.println("前置通知");
    }
    @After("pointCutTest()")
    public void after(JoinPoint joinPoint){
        System.out.println("后置通知");
    }
    @AfterReturning(pointcut="pointCutTest()",returning = "result")
    public void result(JoinPoint joinPoint,Object result){
        System.out.println("返回通知:"+result);
    }

}
