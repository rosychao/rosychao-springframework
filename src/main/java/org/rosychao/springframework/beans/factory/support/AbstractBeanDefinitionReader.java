package org.rosychao.springframework.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.Environment;
import org.rosychao.springframework.core.env.EnvironmentCapable;

/**
 * 对EnvironmentCapable、BeanDefinitionReader类定义的方法进行实现
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader, EnvironmentCapable {

    protected final Log logger = LogFactory.getLog(getClass());

    private final BeanDefinitionRegistry registry;

    private Environment environment;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

}
