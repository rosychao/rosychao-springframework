package org.rosychao.springframework.beans.factory;

/**
 * 用于访问Spring bean容器的根接口。
 * 定义获取Bean及bean的各种属性
 */
public interface BeanFactory {

    /**
     * 返回一个实例
     */
    Object getBean(String name);

    /**
     * 返回与给定对象类型唯一匹配的bean实例
     */
    <T> T getBean(Class<T> requiredType);

}
