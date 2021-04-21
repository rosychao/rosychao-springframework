package org.rosychao.springframework.beans.factory;

/**
 * 定义一个工厂，该工厂在调用时可以返回Object实例（可能是共享的或独立的）
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    /**
     * 返回此工厂管理的对象的实例（可能是共享的或独立的）
     */
    T getObject();

}
