package org.rosychao.springframework.beans.factory.config;

import org.rosychao.springframework.beans.factory.BeanFactory;
import org.rosychao.springframework.beans.factory.support.RootBeanDefinition;

/**
 * 提供创建Bean、自动注入、初始化以及应用bean的后处理器
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 将BeanPostProcessors应用于给定的现有bean实例，
     * 调用其postProcessBeforeInitialization方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, RootBeanDefinition mbd);

    /**
     * 将BeanPostProcessors应用于给定的现有bean实例，
     * 调用其postProcessAfterInitialization方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, RootBeanDefinition mbd);

}
