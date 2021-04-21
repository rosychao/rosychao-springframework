package org.rosychao.springframework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在DefaultSingletonBeanRegistry基础上增加了对FactoryBean的特殊处理功能
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * 由FactoryBeans创建的单例对象的缓存：对象的FactoryBean名称。
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

    public Map<String, Object> getFactoryBeanObjectCache() {
        return factoryBeanObjectCache;
    }

    public void putFactoryBeanObjectCache(String beanName, Object instance){
        factoryBeanObjectCache.put(beanName, instance);
    }
}
