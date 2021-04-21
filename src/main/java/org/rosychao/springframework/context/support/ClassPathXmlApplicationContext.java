package org.rosychao.springframework.context.support;

import javax.servlet.ServletConfig;

/**
 * 独立的XML应用程序上下文，从类路径中获取上下文定义文件
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[] {configLocation}, true);
    }

    public ClassPathXmlApplicationContext(String... configLocations) {
        this(configLocations, true);
    }

    public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh) {
        // 设置配置文件路径
        setConfigLocations(configLocations);
        if (refresh) {
            // 调用ApplicationContext核心的初始化方法
            refresh();
        }
    }

}
