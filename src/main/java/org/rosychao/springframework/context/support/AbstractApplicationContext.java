package org.rosychao.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosychao.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.rosychao.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;

/**
 * ApplicationContext接口的抽象实现
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Properties getConfig() {
        return null;
    }

    @Override
    public void refresh() {

        logger.info("开始初始化ApplicationContext...");

        // 准备刷新的下上文环境
        prepareRefresh();

        // 初始化BeanFactory，并进行XML文件读取（这里模拟Properties）
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // 对BeanFactory进行各种功能填充
        prepareBeanFactory(beanFactory);

        // 子类覆盖方法做额外的处理
        postProcessBeanFactory(beanFactory);

        // 激活各种BeanFactory处理器
        //invokeBeanFactoryPostProcessors(beanFactory);

        // 注册拦截Bean创建的Bean处理，这里只是注册，真正调用在getBean的时候
        //registerBeanPostProcessors(beanFactory);

        // 国家化处理，初始化不同语言的Message源
        //initMessageSource();

        // 初始化应用消息广播器
        //initApplicationEventMulticaster();

        // 留给子类实现给其他bean初始化
        //onRefresh();

        // 在注册的Bean中查找Listener bean，并注册到消息广播器中
        //registerListeners();

        // 初始化剩下的单例（非懒加载）
        finishBeanFactoryInitialization(beanFactory);

        // 完成刷新过程，
        //finishRefresh();

    }

    /**
     * 准备刷新的下上文环境
     */
    protected void prepareRefresh() {
        logger.info("开始准备刷新的下上文环境...");
        // 本版本无需实现
        logger.info("结束准备刷新的下上文环境...");
    }

    /**
     * 初始化BeanFactory，并进行XML文件读取（这里模拟Properties）
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        logger.info("开始初始化BeanFactory...");
        refreshBeanFactory();
        logger.info("结束初始化BeanFactory...");
        return getBeanFactory();
    }

    /**
     * 对BeanFactory进行各种功能填充
     */
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        logger.info("开始对BeanFactory进行各种功能填充...");
        // 本版本无需实现
        logger.info("结束对BeanFactory进行各种功能填充...");
    }

    /**
     * 子类覆盖方法做额外的处理
     */
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        logger.info("开始子类覆盖方法做额外的处理...");
        // 本版本无需实现
        logger.info("结束子类覆盖方法做额外的处理...");
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory){
        logger.info("开始实例化非懒加载的BeanDefinition...");
        beanFactory.preInstantiateSingletons();
        logger.info("结束实例化非懒加载的BeanDefinition...");
    }

    /**
     * 获取工厂实例对象
     */
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 刷新BeanFactory，第一次则表示初始化
     */
    protected abstract void refreshBeanFactory();

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBeanFactory().getBean(requiredType);
    }

}
