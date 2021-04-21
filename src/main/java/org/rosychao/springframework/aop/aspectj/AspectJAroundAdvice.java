package org.rosychao.springframework.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 环绕通知
 */
public class AspectJAroundAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAroundAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Exception {
        // ProxyMethodInvocation pmi = (ProxyMethodInvocation) mi;
        // 需要在方法体里面调用 mi.process() 调用下个链
        return invokeAdviceMethod(mi, null);
    }
}
