package org.rosychao.springframework.context.support;

import org.rosychao.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.rosychao.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    /**
     * 上下文中的BeanFactory
     */
    private DefaultListableBeanFactory beanFactory;

    /**
     * 读取配置文件并解析成BeanDefinition
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    /**
     * 初始化工厂
     */
    @Override
    protected final void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        logger.info("初始化的工厂实例：" + beanFactory);
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    /**
     * 创建默认的实例化工厂
     * @return
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

}
