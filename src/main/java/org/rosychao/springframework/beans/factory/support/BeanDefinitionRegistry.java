package org.rosychao.springframework.beans.factory.support;

import org.rosychao.springframework.beans.factory.config.BeanDefinition;
import org.rosychao.springframework.beans.factory.config.BeanPostProcessor;
import org.rosychao.springframework.core.AliasRegistry;

import java.util.List;

/**
 * 包含bean定义的注册表的接口
 *
 * 这是Spring的bean工厂包中唯一封装了bean定义注册的接口。
 * 标准BeanFactory接口仅涵盖对完全配置的工厂实例的访问。
 *
 * 定义对BeanDefinition的各种增删改查
 */
public interface BeanDefinitionRegistry extends AliasRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void putBeanDefinitionNames(String beanName);

    List<BeanPostProcessor> getBeanPostProcessors();

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
