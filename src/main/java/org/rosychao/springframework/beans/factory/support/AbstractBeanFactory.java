package org.rosychao.springframework.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosychao.springframework.beans.factory.config.BeanDefinition;
import org.rosychao.springframework.beans.factory.config.BeanPostProcessor;
import org.rosychao.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 综合FactoryBeanRegistrySupport和ConfigurableBeanFactory
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 要应用的BeanPostProcessors
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null, null, false);
    }

    protected <T> T doGetBean(
            String name, Class<T> requiredType, Object[] args, boolean typeCheckOnly){

        if(getFactoryBeanObjectCache().containsKey(name)){
            // 返回ioc容器实例
            return (T) getFactoryBeanObjectCache().get(name);
        }

        // 读取配置信息
        RootBeanDefinition beanDefinition = (RootBeanDefinition) this.getBeanDefinition(name);

        if (beanDefinition == null) {
            logger.error("【" + name + "】 实例化失败，result：Null...");
            return null;
        }

        // 先从缓存中取，缓存命中则返回缓存中的值
        Object singleton = getSingleton(name, beanDefinition);
        if (singleton != null) {
            return (T) singleton;
        }

        // 判断是否存在，不存在则添加标记正在创建中
        if (!isSingletonCurrentlyInCreation(name)) {
            getSingletonsCurrentlyInCreation().add(name);
        }

        // 实例化对象
        Object bean = createBean(name, beanDefinition, args);

        // 反射创实例化对象
        logger.info("【" + name + "】 实例化整个过程完成...");
        return (T) bean;
    }

    /**
     * 返回给定bean名称的bean定义
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * 为给定的合并bean定义（和参数）创建一个bean实例
     */
    protected abstract Object createBean(String beanName, RootBeanDefinition mbd, Object[] args);

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 将应用于该工厂创建的bean的BeanPostProcessors列表
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanPostProcessors().add(beanPostProcessor);
    }
}
