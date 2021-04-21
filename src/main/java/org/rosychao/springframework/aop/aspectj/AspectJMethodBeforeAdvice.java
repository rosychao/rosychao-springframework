package org.rosychao.springframework.aop.aspectj;

import com.sun.istack.internal.Nullable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.rosychao.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 方法结束后的通知
 */
public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice implements MethodBeforeAdvice, MethodInterceptor {

    public AspectJMethodBeforeAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public void before(Method method, Object[] args, @Nullable Object target) {
       //invokeAdviceMethod(mi, null, null);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        invokeAdviceMethod(mi, null);
        return mi.proceed();
    }
}
