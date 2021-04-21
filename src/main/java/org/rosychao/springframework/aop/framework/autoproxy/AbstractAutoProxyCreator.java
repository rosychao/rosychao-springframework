package org.rosychao.springframework.aop.framework.autoproxy;

import org.rosychao.springframework.aop.config.AopConfig;
import org.rosychao.springframework.aop.framework.AdvisedSupport;
import org.rosychao.springframework.aop.framework.AopProxy;
import org.rosychao.springframework.aop.framework.AopProxyFactory;
import org.rosychao.springframework.aop.framework.DefaultAopProxyFactory;
import org.rosychao.springframework.beans.factory.config.BeanDefinition;
import org.rosychao.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor的基本实现
 */
public abstract class AbstractAutoProxyCreator implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, BeanDefinition mbd) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, BeanDefinition mbd) {
        if (bean != null) {
            return wrapIfNecessary(bean, mbd);
        }
        return bean;
    }

    /**
     * 如有必要，包装给定的bean，即是否有资格被代理
     */
    protected Object wrapIfNecessary(Object bean, BeanDefinition mbd) {
        // 如果有通知，则创建代理对象.
        AopConfig aopConfig = getAopConfig();
        // 根据AOP配置信息获得 AdvisedSupport
        AdvisedSupport advisedSupport = new AdvisedSupport(aopConfig);
        if (aopConfig.isAspectJAutoProxy()) {
            // 设置要代理的目标对象
            advisedSupport.setTarget(bean);
            // 设置要代理目标对象字节码，同时解析方法
            advisedSupport.setTargetClass(mbd.getBeanClassName());
        }
        if(advisedSupport.pointCutMatch()) {
            // 如果pointCut表达式匹配上了，返回代理对象
            bean = createProxy(advisedSupport).getProxy();
        }
        // 否则返还原始对象
        return bean;
    }

    /**
     * 选择代理策略创建
     */
    private AopProxy createProxy(AdvisedSupport advisedSupport) {
        // 创建AopProxy工厂
        AopProxyFactory proxyFactory = new DefaultAopProxyFactory();
        // 根据信息来选择不同的策略创建AopProxy
        return proxyFactory.createAopProxy(advisedSupport);
    }

    public AopConfig getAopConfig() {
        return null;
    }

    public void setAopConfig(AopConfig aopConfig){}



}
