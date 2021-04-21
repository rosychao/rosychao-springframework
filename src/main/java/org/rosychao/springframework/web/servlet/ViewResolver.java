package org.rosychao.springframework.web.servlet;

import java.io.File;

/**
 * 模板名称和模板解析引擎的匹配工作
 */
public class ViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX = ".rosychao";

    private File tempatePathDir;

    public ViewResolver(File tempatePathDir) {
        this.tempatePathDir = tempatePathDir;
    }

    public View resolveViewName(String viewName) {
        if (null == viewName || "".equals(viewName.trim())) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((tempatePathDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return templateFile.exists() ? new View(templateFile) : null;
    }

}
