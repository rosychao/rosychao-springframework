package org.rosychao.springframework.web.context;

import org.rosychao.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * 提供Web应用程序配置的界面
 */
public interface WebApplicationContext extends ApplicationContext {

    ServletContext getServletContext();

}
