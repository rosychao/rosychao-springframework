package org.rosychao.springframework.web.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 任何类型的servlet的超类
 */
public abstract class HttpServletBean extends HttpServlet {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 初始Servlet时的入口
     */
    @Override
    public void init() throws ServletException {
        // 初始化配置文件
        initContextConfigLocation(getServletConfig());
        // 初始化的ServletBean，
        initServletBean();
    }

    /**
     * 读取init-param中的contextConfigLocation
     */
    protected void initContextConfigLocation(ServletConfig servletConfig) {
        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        setContextConfigLocation(contextConfigLocation);
    }

    // 由FrameworkServlet去实现使用
    public void setContextConfigLocation( String contextConfigLocation) {

    }

    /**
     * 子类负责重写此方法以执行自定义初始化
     */
    protected void initServletBean() {
    }

}
