package org.rosychao.springframework.web.context;

import org.rosychao.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * 由可配置的Web应用程序上下文实现的接口
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    void setServletContext(ServletContext servletContext);

    void setServletConfig(ServletConfig servletConfig);

    /**
     * Return the ServletConfig for this web application context, if any.
     */
    ServletConfig getServletConfig();

    /**
     * 使用init-param样式为此Web应用程序上下文设置配置位置
     */
    void setConfigLocation(String configLocation);

    /**
     * 设置此Web应用程序上下文的配置位置
     */
    void setConfigLocations(String... configLocations);


}
