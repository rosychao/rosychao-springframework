package org.rosychao.springframework.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosychao.springframework.beans.factory.config.BeanDefinition;
import org.rosychao.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.rosychao.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主要是对Bean注册后的处理
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 存储注册信息的BeanDefinition
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap(256);

    /**
     * Bean定义名称的列表，按注册顺序
     */
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null, null, false);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return (T) this.getBean(requiredType.getName());
    }

    /**
     * 创建对象，利用三级缓存解决循环依赖
     *
     * @return
     */

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
    }

    @Override
    public void putBeanDefinitionNames(String beanName) {
        this.beanDefinitionNames.add(beanName);
    }

    @Override
    public void preInstantiateSingletons() {
        // 用个新的List去接受，防止原数据改变导致异常
        List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
        // 初始化所有非延时加载的Bean
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 判断是否懒加载，非懒加载的BeanDefinition才进行实例化
            if (!beanDefinition.isLazyInit()) {
                getBean(beanName);
            }
        }
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return StringUtils.toStringArray(this.beanDefinitionNames);
    }


}