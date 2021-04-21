package org.rosychao.springframework.web.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosychao.springframework.context.ApplicationContext;
import org.rosychao.springframework.stereotype.Controller;
import org.rosychao.springframework.util.StringUtils;
import org.rosychao.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

public class DispatcherServlet extends FrameworkServlet {

    /**
     * 默认模板存放位置
     */
    public static final String DEFAULT_TEMPLATE_PATH = "template";

    /**
     * 默认模板后缀
     */
    public static final String DEFAULT_TEMPLATE_SUFFIX = ".rosychao";

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 此servlet使用的HandlerMappings列表。
     */
    private List<HandlerMapping> handlerMappings = new ArrayList();

    /**
     * 此servlet使用的HandlerAdapter列表
     */
    private Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap();

    /**
     * 此servlet使用的ViewResolvers列表。
     */
    private List<ViewResolver> viewResolvers = new ArrayList();
    ;

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    /**
     * 处理实际的分派给处理程序。
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先根据请求获取Handler
        HandlerMapping mappedHandler = getHandler(request);
        // 如果没有找到对应的Handler则响应404
        if (mappedHandler == null) {
            noHandlerFound(request, response);
            return;
        }
        // 获取适配器，准备请求参数
        HandlerAdapter ha = getHandlerAdapter(mappedHandler);
        // 返回ModelAndView存储了要穿页面上值，和页面模板的名称
        ModelAndView mv = null;
        try {
            mv = ha.handle(request, response, mappedHandler);
        } catch (Exception e) {
            e.printStackTrace();
            errorHandler(request, response, e);
        }
        // 输出结果
        processDispatchResult(request, response, mappedHandler, mv, null);
    }

    /**
     * 根据请求路径返回Handler映射处理器
     */
    protected HandlerMapping getHandler(HttpServletRequest request) {
        if (this.handlerMappings == null) {
            return null;
        }
        // 获取请求路径
        String requestURI = request.getRequestURI();
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            // 如果匹配到了
            if (handlerMapping.getPattern().matcher(requestURI).matches()) {
                return handlerMapping;
            }
        }
        return null;
    }

    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> model = new HashMap();
        model.put("url", request.getRequestURI());
        this.processDispatchResult(request, response, null, new ModelAndView("404", model), null);
    }

    protected void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        Map<String, Object> model = new HashMap();
        model.put("message", e.getCause().getMessage());
        model.put("stackTrace", Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", ""));
        this.processDispatchResult(request, response, null, new ModelAndView("500", model), e);
    }

    protected HandlerAdapter getHandlerAdapter(Object handler) {
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        HandlerAdapter ha = this.handlerAdapters.get(handler);
        // 判断是否是支持的处理器类型
        return ha.supports(handler) ? ha : null;
    }

    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                       HandlerMapping mappedHandler, ModelAndView mv, Exception e) throws ServletException, IOException {
        render(mv, request, response);
    }

    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(null == mv){return;}

        for (ViewResolver viewResolver : this.viewResolvers) {
            View view = viewResolver.resolveViewName(mv.getViewName());
            if (view == null) {
                // 说明不存在
                response.setContentType("text/html;charset=utf-8");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(mv.getViewName() + "文件模板不存在！");
                return;
            }
            view.render(mv.getModel(),request,response);
            return;
        }
    }

    /**
     * 此实现调用initStrategies
     */
    @Override
    protected void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    /**
     * 初始化MVC组件的关键方法
     */
    protected void initStrategies(ApplicationContext context) {
        // 处理文件上传的组件
        initMultipartResolver(context);
        // 初始化国际本地化配置
        initLocaleResolver(context);
        // 初始化主题
        initThemeResolver(context);
        // 初始化映射到处理器的关系
        initHandlerMappings(context);
        // 初始化处理器的适配器
        initHandlerAdapters(context);
        // 处理接口异常的组件
        initHandlerExceptionResolvers(context);
        // 处理返回String的情况
        initRequestToViewNameTranslator(context);
        // 处理数据和视图渲染的组件
        initViewResolvers(context);
        // 具有存储属性，理重定向等
        initFlashMapManager(context);
    }

    private void initMultipartResolver(ApplicationContext context) {
        // 本版本不实现
    }

    private void initLocaleResolver(ApplicationContext context) {
        // 本版本不实现
    }

    private void initThemeResolver(ApplicationContext context) {
        // 本版本不实现
    }

    private void initHandlerMappings(ApplicationContext context) {
        // 获取所有注册进去的Bean对象名列表
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // 通过名称拿到Bean实例对象
            Object beanInstance = context.getBean(beanDefinitionName);
            // 获取class
            Class<?> beanClass = beanInstance.getClass();
            // 判断如果实例对象没加@Controller注解则跳过处理
            if (!beanClass.isAnnotationPresent(Controller.class)) {
                continue;
            }
            // 根据@RequestMapping解析出接口路径
            String url = "";
            // 先获取类上的
            if (beanClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = beanClass.getAnnotation(RequestMapping.class);
                url = annotation.value().trim();
            }
            // 再获取方法上的
            Method[] methods = beanClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                // 拼接： 类上路径地址+方法上路径地址
                String reqUrl = ("/" + url + "/" + annotation.value().trim()).replaceAll("\\*", ".*").replaceAll("/+", "/");
                // 用正则表达式保存方便判断
                Pattern pattern = Pattern.compile(reqUrl);
                // 加入到HandlerMapping容器中
                this.handlerMappings.add(new HandlerMapping(beanInstance, method, pattern));
                logger.info("Mapped " + reqUrl + " -> " + method);
            }
        }
    }

    private void initHandlerAdapters(ApplicationContext context) {
        // 一个HandlerMapping对应一个HandlerAdapter
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            this.handlerAdapters.put(handlerMapping, new HandlerAdapter());
        }

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
        // 本版本不实现
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
        // 本版本不实现
    }

    private void initViewResolvers(ApplicationContext context) {
        // 取得存放模板的路径地址
        String templatePath = context.getConfig().getProperty("templatePath");
        if (StringUtils.isEmpty(templatePath)) {
            templatePath = this.DEFAULT_TEMPLATE_PATH;
        }
        try {
            templatePath = Thread.currentThread().getContextClassLoader().getResource(templatePath).toURI().getPath();
            File tempatePathDir = new File(templatePath);
            for (File file : tempatePathDir.listFiles()) {
                // 如果fileName不是 .rosychao 结尾的，则不加入
                if (!file.getName().endsWith(this.DEFAULT_TEMPLATE_SUFFIX)) {
                    continue;
                }
                this.viewResolvers.add(new ViewResolver(tempatePathDir));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void initFlashMapManager(ApplicationContext context) {
        // 本版本不实现
    }

}
