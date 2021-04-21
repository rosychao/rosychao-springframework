package org.rosychao.springframework.aop.framework;

/**
 * 使用Cglib来实现动态代理
 * 未实现，所以只支持带接口的类进行动态代理
 */
public class CglibAopProxy implements AopProxy {

    protected final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        // 没有引入Cglib包，不作实现
        return null;
    }
}
