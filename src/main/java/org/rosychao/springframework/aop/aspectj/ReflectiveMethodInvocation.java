package org.rosychao.springframework.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.rosychao.springframework.aop.ProxyMethodInvocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用反射调用目标对象
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {

    private Object proxy;
    private Object target;
    private Method method;
    private Object[] arguments;
    private Class<?> targetClass;
    private Map<String, Object> userAttributes;
    private List<Object> interceptorsAndDynamicMethodMatchers;

    // 定义一个索引，从-1开始来记录当前拦截器执行的位置
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object proceed() throws Exception {
        // 递归结束条件：如果Interceptor执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target,this.arguments);
        }

        // 获取下一个Interceptor
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor) {
            MethodInterceptor dm = (MethodInterceptor) interceptorOrInterceptionAdvice;
            return dm.invoke(this);
        } else {
            // 动态匹配失败时,略过当前Interceptor,调用下一个Interceptor
            return proceed();
        }
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (this.userAttributes == null) {
                this.userAttributes = new HashMap();
            }
            this.userAttributes.put(key, value);
        }
        else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return (this.userAttributes != null ? this.userAttributes.get(key) : null);
    }

}
