package org.rosychao.springframework.beans.factory.config;

/**
 * 工厂挂钩允许对新bean实例进行自定义修改
 */
public interface BeanPostProcessor {

    /**
     * 在任何bean初始化回调之前
     */
    default Object postProcessBeforeInitialization(Object bean, BeanDefinition mbd) {
        return bean;
    }

    /**
     * 在任何bean初始化回调之后
     */
    default Object postProcessAfterInitialization(Object bean, BeanDefinition mbd) {
        return bean;
    }

}
