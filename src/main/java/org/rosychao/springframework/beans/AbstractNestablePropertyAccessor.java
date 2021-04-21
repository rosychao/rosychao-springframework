package org.rosychao.springframework.beans;

/**
 * 为所有典型的用例提供必要的基础结构
 */
public abstract class AbstractNestablePropertyAccessor {

    Object wrappedObject;

    protected AbstractNestablePropertyAccessor(Object object) {
        setWrappedInstance(object);
    }

    public void setWrappedInstance(Object object) {
        this.wrappedObject = object;
    }

    public final Object getWrappedInstance() {
        return this.wrappedObject;
    }

    public final Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }
}
