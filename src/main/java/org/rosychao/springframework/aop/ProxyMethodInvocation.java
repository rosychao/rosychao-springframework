package org.rosychao.springframework.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 允许访问通过其进行方法调用的代理
 */
public interface ProxyMethodInvocation extends MethodInvocation {

    /**
     * 将具有给定值的指定用户属性添加到此调用。
     */
    void setUserAttribute(String key, Object value);

    /**
     * 返回指定用户属性的值
     */
    Object getUserAttribute(String key);


}
