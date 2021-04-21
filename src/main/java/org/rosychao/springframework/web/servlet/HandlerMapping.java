package org.rosychao.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 由定义请求和处理程序对象之间的映射关系的对象
 */
public class HandlerMapping {

    /**
     * 保存方法对应的实例
     */
    private Object controller;
    /**
     * 保存映射的方法
     */
    private Method method;
    /**
     * URL的正则匹配
     */
    private Pattern pattern;

    public HandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }


}
