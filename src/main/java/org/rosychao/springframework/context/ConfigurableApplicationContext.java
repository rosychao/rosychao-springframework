package org.rosychao.springframework.context;

/**
 * 接口将由大多数应用程序上下文实现
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 上下文的初始化
     */
    void refresh();

}
