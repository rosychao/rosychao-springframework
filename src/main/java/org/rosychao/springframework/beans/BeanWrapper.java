package org.rosychao.springframework.beans;

/**
 * 主要用于封装创建后的对象，代理对象或者原生对象
 */
public interface BeanWrapper {

    /**
     * 返回此对象包装的bean实例
     */
    Object getWrappedInstance();


    /**
     * 返回包装的bean实例的类型
     */
    Class<?> getWrappedClass();

}
