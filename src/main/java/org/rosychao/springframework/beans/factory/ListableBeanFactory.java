package org.rosychao.springframework.beans.factory;

/**
 * 根据各种条件获取bean的配置清单
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 返回工厂定义的bean数
     */
    int getBeanDefinitionCount();

    /**
     * 返回此工厂中定义的所有bean的名称
     */
    String[] getBeanDefinitionNames();

}
