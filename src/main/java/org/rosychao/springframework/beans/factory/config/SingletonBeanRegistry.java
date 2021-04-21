package org.rosychao.springframework.beans.factory.config;

/**
 * 为共享bean实例定义注册表的接口
 * 定义对单例的注册及获取
 */
public interface SingletonBeanRegistry {

    /**
     * 返回以给定名称注册的（原始）单例对象
     */
    Object getSingleton(String beanName, BeanDefinition beanDefinition);

}
