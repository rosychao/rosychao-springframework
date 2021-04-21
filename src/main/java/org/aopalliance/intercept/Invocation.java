package org.aopalliance.intercept;

/**
 * 该接口表示程序中的调用
 */
public interface Invocation extends Joinpoint {

    /**
     * 获取参数作为数组对象。 可以更改此数组中的元素值以更改参数
     */
    Object[] getArguments();

}
