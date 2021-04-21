package org.rosychao.springframework.beans.factory.support;

public class BeanDefinitionReaderUtils {

    /**
     * 把每一个配置信息解析成一个BeanDefinition
     */
    public static AbstractBeanDefinition createBeanDefinition(String beanClassName, String factoryBeanName){
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

}
