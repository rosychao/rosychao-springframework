package org.rosychao.springframework.web.context.support;

import org.rosychao.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.rosychao.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * 将资源路径解释为Servlet上下文资源
 */
public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext
        implements ConfigurableWebApplicationContext {

    /**
     * 运行此上下文的Servlet上下文。
     */
    private ServletContext servletContext;

    /**
     * 运行此上下文的Servlet配置
     */
    private ServletConfig servletConfig;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
        if (servletConfig != null && this.servletContext == null) {
            setServletContext(servletConfig.getServletContext());
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

}
