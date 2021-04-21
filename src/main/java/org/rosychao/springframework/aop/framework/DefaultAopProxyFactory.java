package org.rosychao.springframework.aop.framework;

/**
 * 默认的AopProxyFactory实现，创建CGLIB代理或JDK动态代理
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    /**
     * 如果有接口，则使用JDK动态代理，否则使用Cglib动态代理
     */
    public AopProxy createAopProxy(AdvisedSupport config) {
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }

}
