package org.rosychao.springframework.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 放生在之后的通知
 */
public class AspectJAfterAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        try {
            return mi.proceed();
        } finally {
            invokeAdviceMethod(mi, null);
        }
    }
}