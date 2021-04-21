package org.rosychao.springframework.web.servlet;

import org.rosychao.springframework.beans.BeanUtils;
import org.rosychao.springframework.context.ApplicationContext;
import org.rosychao.springframework.web.context.ConfigurableWebApplicationContext;
import org.rosychao.springframework.web.context.WebApplicationContext;
import org.rosychao.springframework.web.support.XmlWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Web框架的基础servlet。 在基于JavaBean的整体解决方案中提供与Spring应用程序上下文的集成。
 */
public abstract class FrameworkServlet extends HttpServletBean{

    /**
     * FrameworkServlet的默认上下文类
     */
    public static final Class<?> DEFAULT_CONTEXT_CLASS = XmlWebApplicationContext.class;


    /**
     * 显式上下文配置位置
     */
    private String contextConfigLocation;

    /**
     * 此Servlet的WebApplicationContext。
     */
    private WebApplicationContext webApplicationContext;

    /**
     * 创建WebApplicationContext实现类。
     */
    private Class<?> contextClass = DEFAULT_CONTEXT_CLASS;

    /**
     * 返回自定义上下文类，无修改则是默认
     */
    public Class<?> getContextClass() {
        return this.contextClass;
    }

    /**
     * 设置上下文配置位置
     */
    @Override
    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }

    /**
     * 返回显式上下文配置位置（如果有）
     */
    public String getContextConfigLocation() {
        return this.contextConfigLocation;
    }

    @Override
    protected final void initServletBean() {
        // 初始化WebApplicationContext
        this.webApplicationContext = initWebApplicationContext();
        // 初始化FrameworkServlet，无实现，方便子类去扩展
        initFrameworkServlet();
    }

    /**
     * 初始化并发布此Servlet的WebApplicationContext
     */
    protected WebApplicationContext initWebApplicationContext() {
         WebApplicationContext wac = createWebApplicationContext();
        onRefresh(wac);
         return wac;
    }

    /**
     * 默认实例化此servlet的WebApplicationContext，
     */
    protected WebApplicationContext createWebApplicationContext() {
        // 获取默认初始化的WebXmlApplicationContext
        Class<?> contextClass = getContextClass();
        ConfigurableWebApplicationContext wac = null;
        try {
            wac = (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);
            String configLocation = getContextConfigLocation();
            if (configLocation != null) {
                wac.setConfigLocation(configLocation);
            }
            configureAndRefreshWebApplicationContext(wac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wac;
    }

    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac) {
        wac.setServletContext(getServletContext());
        wac.setServletConfig(getServletConfig());
        postProcessWebApplicationContext(wac);
        // 刷新上下文的核心方法
        wac.refresh();
    }

    /**
     * 刷新Servlet，在DispatchServlet中实现
     */
    protected void onRefresh(ApplicationContext context) {

    }

    protected void initFrameworkServlet(){}

    /**
     * 用于在刷新给定的WebApplicationContext之前进行的后处理操作
     * 无实现，方便以后子类扩展
     */
    protected void postProcessWebApplicationContext(ConfigurableWebApplicationContext wac) {
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * 处理此请求
     * 实际的事件处理由抽象的doService模板方法执行
     */
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doService(request, response);
    }

    /**
     * 子类必须实现此方法来执行请求处理，并接收针对GET，POST，PUT和DELETE的集中式回调
     */
    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
