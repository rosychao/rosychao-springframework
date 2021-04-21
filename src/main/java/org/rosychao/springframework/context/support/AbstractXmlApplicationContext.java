package org.rosychao.springframework.context.support;

import org.rosychao.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.rosychao.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        logger.info("开始读取配置文件转换成BeanDefinition...");
        // 这里模拟读取Properties
        PropertiesBeanDefinitionReader beanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        logger.info("创建配置文件解析器：" + beanDefinitionReader);
        // 调用解析器去解析
        loadBeanDefinitions(beanDefinitionReader);
        logger.info("结束读取配置文件转换成BeanDefinition...");
    }

    protected void loadBeanDefinitions(PropertiesBeanDefinitionReader reader) {
        reader.loadBeanDefinitions(getConfigLocations());
    }

}
