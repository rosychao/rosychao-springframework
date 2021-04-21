package org.rosychao.springframework.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 抛出异常后的通知
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterThrowingAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        try {
            return mi.proceed();
        } catch (Throwable ex) {
            invokeAdviceMethod(mi, new Exception(ex));
            throw ex;
        }
    }

    @Override
    public void setThrowingName(String name) {
        setThrowingNameNoCheck(name);
    }
}
