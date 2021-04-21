package org.rosychao.springframework.test.aspect;

import org.aspectj.lang.annotation.*;

/**
 * 配置切面
 */
@Aspect
public class AspectAopConfig {

    @Pointcut("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void test(){

    }

    @Before("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testBefore(){
        System.out.println("...before...");
    }

    @After("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testAfter(){
        System.out.println("...after...");
    }

    @Around("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testAround(){
        System.out.println("...around...");
    }

    @AfterThrowing(throwing="java.lang.Exception")
    public void testAfterThrowing(Throwable ex){
        System.out.println("...异常：" + ex.getMessage() + "...");
    }
}
