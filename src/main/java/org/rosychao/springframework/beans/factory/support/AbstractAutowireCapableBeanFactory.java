package org.rosychao.springframework.beans.factory.support;

import org.rosychao.springframework.beans.BeanUtils;
import org.rosychao.springframework.beans.BeanWrapper;
import org.rosychao.springframework.beans.BeanWrapperImpl;
import org.rosychao.springframework.beans.factory.annotation.Autowired;
import org.rosychao.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.rosychao.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合AbstractBeanFactory并对接口 Autowire CaoableBeanFactory进行实现
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 未完成的FactoryBean实例的高速缓存：BeanWrapper的FactoryBean名称
     */
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new HashMap<>(16);

    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, Object[] args) {
        return doCreateBean(beanName, mbd, args);
    }

    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args) {
        BeanWrapper instanceWrapper = createBeanInstance(beanName, mbd, args);
        logger.info("Bean实例化：" + instanceWrapper);
        populateBean(beanName, mbd, instanceWrapper);
        logger.info("Bean赋值属性：" + instanceWrapper);
        Object instance = initializeBean(beanName, instanceWrapper.getWrappedInstance(), mbd);
        factoryBeanInstanceCache.put(beanName, instanceWrapper);
        putFactoryBeanObjectCache(beanName, instance);
        factoryBeanInstanceCache.put(mbd.getFactoryBeanName(), instanceWrapper);
        putFactoryBeanObjectCache(mbd.getFactoryBeanName(), instance);
        return instance;
    }

    /**
     * 使用适当的实例化策略为指定的bean创建一个新实例
     */
    protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, Object[] args) {
        return instantiateBean(beanName, mbd);
    }

    /**
     * 使用其默认构造函数实例化给定的bean
     */
    protected BeanWrapper instantiateBean(String beanName, RootBeanDefinition mbd) {
        final Class<?> clazz;
        Constructor<?> constructorToUse = null;
        BeanWrapper bw = null;
        try {
            clazz = Class.forName(mbd.getBeanClassName());
            constructorToUse = clazz.getDeclaredConstructor();
            Object beanInstance = BeanUtils.instantiateClass(constructorToUse);
            for (Class<?> i : clazz.getInterfaces()) {
                putSingleObject(i.getName(), beanInstance);
            }
            putSingleObject(beanName, beanInstance);
            putSingleObject(mbd.getFactoryBeanName(), beanInstance);
            bw = new BeanWrapperImpl(beanInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bw;
    }

    /**
     * 使用Bean定义中的属性值填充给定BeanWrapper中的Bean实例
     */
    protected void populateBean(String beanName, RootBeanDefinition mbd, BeanWrapper bw) {
        // 实例对象，待注入属性
        Object instance = bw.getWrappedInstance();
        // 反射类对象
        Class<?> clazz = bw.getWrappedClass();
        //忽略字段的修饰符
        for (Field field : clazz.getDeclaredFields()) {
            // 如果没有Autowired注解，则没有依赖注入
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            // 获取autowired注解名称
            String autowiredBeanName = field.getAnnotation(Autowired.class).value().trim();
            // 如果没有自定义名称的话
            if ("".equals(autowiredBeanName)) {
                // 则采用字段名字
                autowiredBeanName = field.getType().getName();
            }
            //强制访问
            field.setAccessible(true);
            try {
                // 依赖注入
                field.set(instance, getBean(autowiredBeanName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化给定的bean实例，应用工厂回调以及init方法和bean post处理器
     * AOP 入口，在后置处理器中完成
     */
    protected Object initializeBean(String beanName, Object bean, RootBeanDefinition mbd) {
        // 调用填充方法
        // invokeAwareMethods(beanName, bean);

        // 前置处理器
        bean = applyBeanPostProcessorsBeforeInitialization(bean, mbd);

            // 执行吃实话方法
        // invokeInitMethods(beanName, wrappedBean, mbd);

        // 后置处理器
        bean = applyBeanPostProcessorsAfterInitialization(bean, mbd);
        return bean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, RootBeanDefinition mbd) {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, mbd);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, RootBeanDefinition mbd) {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, mbd);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

}
