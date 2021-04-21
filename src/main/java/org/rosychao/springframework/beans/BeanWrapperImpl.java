package org.rosychao.springframework.beans;

/**
 * 默认的BeanWrapper实现
 * 主要实现的方法在其弗父类AbstractNestablePropertyAccessor
 */
public class BeanWrapperImpl extends AbstractNestablePropertyAccessor implements BeanWrapper {

    public BeanWrapperImpl(Object object) {
        super(object);
    }

}
