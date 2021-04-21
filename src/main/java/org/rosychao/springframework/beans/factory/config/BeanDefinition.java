package org.rosychao.springframework.beans.factory.config;

/**
 * BeanDefinition描述了一个bean实例，该实例具有属性值，构造函数参数值以及具体实现所提供的更多信息。
 */
public interface BeanDefinition {

    /**
     * 指定此Bean定义的全限定类名
     */
    void setBeanClassName(String beanClassName);

    /**
     * 返回此Bean定义的全限定类名
     */
    String getBeanClassName();

    /**
     * 设置是否应延迟初始化此bean
     */
    void setLazyInit(boolean lazyInit);

    /**
     * 返回此bean是否应延迟初始化
     */
    boolean isLazyInit();

    /**
     * 指定bean的名称,这是调用指定工厂方法的bean的名称
     */
    void setFactoryBeanName(String factoryBeanName);

    /**
     * 返回工厂bean名称
     */
    String getFactoryBeanName();

}
