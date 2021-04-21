package org.rosychao.springframework.beans.factory.config;

import org.rosychao.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * 大多数bean工厂都将实现配置接口
 * 提供配置Factory的各种方法
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
}
