package org.rosychao.springframework.beans.factory.config;

import org.rosychao.springframework.beans.factory.ListableBeanFactory;

/**
 * 由大多数可列出的bean工厂实现的配置接口。
 * BeanFactory配置清单，指定忽略类型及接口
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 确保所有非延迟初始单例都实例化
     */
    void preInstantiateSingletons();

}
