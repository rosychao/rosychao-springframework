package org.rosychao.springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

/**
 * 封装AspectJ方面或带有AspectJ注释的建议方法的AOP Alliance Advice类的基类。
 */
public class AbstractAspectJAdvice implements Advice {

    /**
     * 被代理的方法
     */
    private final Method aspectMethod;
    /**
     * 被代理的对象
     */
    private final Object aspectTarget;
    /**
     * 抛出异常捕获的异常名称
     */
    private String throwingName;

    public AbstractAspectJAdvice(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    /**
     * 执行切面方法
     */
    protected Object invokeAdviceMethod(Joinpoint joinPoint, Throwable tx) throws Exception {
        // 先获取参数列表
        Class<?>[] paramTypes = this.aspectMethod.getParameterTypes();
        if (paramTypes.length == 0) {
            // 如果无参数，直接执行方法
            return this.aspectMethod.invoke(aspectTarget);
        } else {
            // 封装参数列表
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                if (paramTypes[i] == JoinPoint.class) {
                    args[i] = joinPoint;
                } else if (paramTypes[i] == Throwable.class) {
                    args[i] = tx;
                } else if (paramTypes[i] == Object.class) {
                    args[i] = null;
                }
            }
            // 执行增强通知方法
            return this.aspectMethod.invoke(aspectTarget, args);
        }
    }

    /**
     * 设置捕获异常的名称，子类实现
     */
    public void setThrowingName(String name) {
        throw new UnsupportedOperationException("Only afterThrowing advice can be used to bind a thrown exception");
    }

    /**
     * 获取捕获异常的名称
     */
    public String getThrowingName() {
        return throwingName;
    }

    /**
     * 设置捕获异常的名称
     */
    protected void setThrowingNameNoCheck(String name) {
        this.throwingName = name;
    }

}
