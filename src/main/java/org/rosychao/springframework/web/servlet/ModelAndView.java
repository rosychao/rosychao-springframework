package org.rosychao.springframework.web.servlet;

import java.util.Map;

/**
 * 用于风阻行页面模板和数据模型
 */
public class ModelAndView {

    /**
     * 页面模板的名称
     */
    private String viewName;

    /**
     * 往页面传送的传输
     */
    private Map<String,?> model;

    public ModelAndView() {
    }

    public ModelAndView(String viewName) { this.viewName = viewName; }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
