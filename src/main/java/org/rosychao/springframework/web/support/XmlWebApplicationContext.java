package org.rosychao.springframework.web.support;

import org.rosychao.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.rosychao.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.rosychao.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

import java.util.Properties;

/**
 * Web上下文，基于XML配置文件(此处模拟Properties)
 */
public class XmlWebApplicationContext extends AbstractRefreshableWebApplicationContext {

    PropertiesBeanDefinitionReader reader;

    public Properties getConfig(){
        return this.reader.getProps();
    }

    /**
     * 通过XmlBeanDefinitionReader加载Bean定义(此处模拟从Properties中加载)
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        logger.info("开始读取配置文件转换成BeanDefinition...");
        // 这里模拟读取Properties
        reader = new PropertiesBeanDefinitionReader(beanFactory);
        logger.info("创建配置文件解析器：" + reader);
        // 调用解析器去解析
        loadBeanDefinitions(reader);
        logger.info("结束读取配置文件转换成BeanDefinition...");
    }

    /**
     * 使用给定的XmlBeanDefinitionReader加载bean定义(此处模拟从Properties中加载)
     */
    protected void loadBeanDefinitions(PropertiesBeanDefinitionReader reader) {
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            for (String configLocation : configLocations) {
                reader.loadBeanDefinitions(configLocation);
            }
        }
    }

}
