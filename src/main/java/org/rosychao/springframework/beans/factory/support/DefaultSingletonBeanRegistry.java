package org.rosychao.springframework.beans.factory.support;

import org.rosychao.springframework.beans.BeanUtils;
import org.rosychao.springframework.beans.BeanWrapper;
import org.rosychao.springframework.beans.BeanWrapperImpl;
import org.rosychao.springframework.beans.factory.config.BeanDefinition;
import org.rosychao.springframework.core.SimpleAliasRegistry;
import org.rosychao.springframework.beans.factory.config.SingletonBeanRegistry;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 共享bean实例的通用注册表，实现SingletonBeanRegistry 。
 * 允许注册应通过Bean名称获得的注册中心所有调用者共享的单例实例。
 */
public abstract class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存：存放单例对象，Bean名称-Bean实例
     */
    private final Map<String, Object> singletonObjects = new HashMap<>(256);

    /**
     * 三级缓存：存放单例对象，Bean名称-ObjectFactory
     */
    // private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);


    /**
     * 二级缓存：存放早期的单例对象，Bean名称-Bean实例
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 存放当前正在创建的bean的名称
     */
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new HashMap<>(16));

    public Set<String> getSingletonsCurrentlyInCreation() {
        return this.singletonsCurrentlyInCreation;
    }

    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    @Override
    public Object getSingleton(String beanName, BeanDefinition beanDefinition) {
        //先去一级缓存里面拿
        Object bean = singletonObjects.get(beanName);
        //如果一级缓存中没有，但是又有创建标识，说明就是循环依赖
        if (bean == null && singletonsCurrentlyInCreation.contains(beanName)) {
            bean = earlySingletonObjects.get(beanName);
            //如果二级缓存也没有，那就从三级缓存中拿
            if (bean == null) {
                final Class<?> clazz;
                Constructor<?> constructorToUse = null;
                BeanWrapper bw = null;
                try {
                    clazz = Class.forName(beanDefinition.getBeanClassName());
                    constructorToUse = clazz.getDeclaredConstructor();
                    Object beanInstance = BeanUtils.instantiateClass(constructorToUse);
                    bw = new BeanWrapperImpl(beanInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //将创建出来的对象重新放入到二级缓存中
                earlySingletonObjects.put(beanName, bw);
            }
        }
        return bean;
    }

    /**
     * 将实例添加进一级缓存
     */
    public void putSingleObject(String beanName, Object instance) {
        //  加入一级缓存
        this.singletonObjects.put(beanName, instance);
    }

}
