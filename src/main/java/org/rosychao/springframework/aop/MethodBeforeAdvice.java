package org.rosychao.springframework.aop;

import java.lang.reflect.Method;

/**
 * 在调用方法之前先调用
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    /**
     * 调用给定方法之前的回调
     */
    void before(Method method, Object[] args, Object target);

}
