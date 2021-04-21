package org.aopalliance.intercept;

/**
 * 拦截在到达目标的接口上的调用
 */
public interface MethodInterceptor extends Interceptor {

    /**
     * 实现此方法在调用之前和之后执行额外的处理
     */
    Object invoke(MethodInvocation invocation) throws Exception;

}
