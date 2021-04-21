package org.rosychao.springframework.aop.framework;

/**
 * 由能够基于AdvisedSupport配置对象创建AOP代理的工厂实现的接口
 */
public interface AopProxyFactory {

    /**
     * 为给定的AOP配置创建一个AopProxy
     */
    AopProxy createAopProxy(AdvisedSupport config);

}
