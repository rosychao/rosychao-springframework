package org.rosychao.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;
import org.rosychao.springframework.aop.aspectj.ReflectiveMethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 使用JDK的Proxy创建动态代理对象
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    /**
     * Config用于配置此代理
     */
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    /**
     * 创建代理对象，需要有实现接口
     */
    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

    /**
     * 代理后的方法的入口
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, this.advised.getTarget(), method, args, this.advised.getTargetClass(), interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }

}
