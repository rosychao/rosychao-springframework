package org.aopalliance.intercept;

import java.lang.reflect.InvocationTargetException;

/**
 * 该接口表示通用的运行时连接点
 */
public interface Joinpoint {

    /**
     * 继续执行链中的下一个拦截器
     */
    Object proceed() throws InvocationTargetException, IllegalAccessException, Exception;

    /**
     * 返回保存当前联接点的静态部分的对象
     */
    Object getThis();

}
