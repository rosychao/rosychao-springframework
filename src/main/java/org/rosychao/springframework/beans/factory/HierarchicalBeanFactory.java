package org.rosychao.springframework.beans.factory;

/**
 * 由bean工厂实现的子接口，可以是层次结构的一部分
 * 增加了对parentFactory的支持
 */
public interface HierarchicalBeanFactory extends BeanFactory {
}
