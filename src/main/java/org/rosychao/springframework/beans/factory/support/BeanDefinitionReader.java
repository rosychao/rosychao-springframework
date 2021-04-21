package org.rosychao.springframework.beans.factory.support;

/**
 * Bean定义阅读器的简单界面。 指定带有“资源”和“字符串”位置参数的加载方法
 * 主要定义资源文件读取并转换为BeanDefinition的各个功能
 */
public interface BeanDefinitionReader {

    /**
     * 返回Bean工厂以向其注册Bean定义
     */
    BeanDefinitionRegistry getRegistry();


    /**
     * 从指定的资源位置加载bean定义
     */
    void loadBeanDefinitions(String location);

    /**
     * 从指定的资源位置加载bean定义
     */
    void loadBeanDefinitions(String... location);

}
