package org.aopalliance.intercept;

import java.lang.reflect.Method;

/**
 * 方法调用的描述，在方法调用时提供给拦截器。
 */
public interface MethodInvocation extends Invocation {

    /**
     * 获取被调用的方法
     */
    Method getMethod();

}
