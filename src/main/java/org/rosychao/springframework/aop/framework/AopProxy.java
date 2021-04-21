package org.rosychao.springframework.aop.framework;

/**
 * 用于配置的AOP代理的委托接口，允许创建实际的代理对象
 */
public interface AopProxy {

    /**
     * 创建一个新的代理对象
     */
    Object getProxy();

    /**
     * 创建一个新的代理对象
     */
    Object getProxy(ClassLoader classLoader);
}
