package org.rosychao.springframework.aop.aspectj.annotation;

import org.rosychao.springframework.aop.config.AopConfig;
import org.rosychao.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;

/**
 * 用于处理当前应用程序上下文中的所有AspectJ注释方面以及Spring Advisor。
 */
public class AnnotationAwareAspectJAutoProxyCreator extends AbstractAutoProxyCreator {

    /**
     * Aop配置类
     */
    private AopConfig aopConfig = null;

    @Override
    public AopConfig getAopConfig() {
        return this.aopConfig;
    }

    @Override
    public void setAopConfig(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

}
