package org.rosychao.springframework.web.servlet;

import org.rosychao.springframework.util.StringUtils;
import org.rosychao.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

/**
 * 完成请求传递到服务端的参数列表和方法实参列表的对应关系
 * 核心方法 handle():处理参数值的类型转换
 */
public class HandlerAdapter {

    boolean supports(Object handler) {
        return handler instanceof HandlerMapping;
    }

    /**
     * 处理请求参数和方法参数匹配，然后执行方法，封装成ModelAndView返回去
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handler) throws InvocationTargetException, IllegalAccessException, IOException {

        // 行参列表
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        // 实参列表
        Object[] parameterValues = new Object[parameterTypes.length];
        // 请求的数据
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 处理带有@RequestParam的参数
        Annotation[][] parameterAnnotations = handler.getMethod().getParameterAnnotations();
        // 获取方法参数上的注解 第一层为参数，第二层为参数上注解
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                // 遍历参数上注解列表
                if (annotation.annotationType() == RequestParam.class) {
                    // 如果注解为RequestParam
                    String requestParamValue = ((RequestParam) annotation).value().trim();
                    if (!StringUtils.isEmpty(requestParamValue)) {
                        String value = Arrays.toString(parameterMap.get(requestParamValue)).replaceAll("\\[|\\]", "")
                                .replaceAll("\\s", ",");
                        parameterValues[i] = this.caseStringValue(value, parameterTypes[i]);
                    }
                }
            }
        }

        // 处理request和respond
        for (int i = 0; i < parameterValues.length; i++) {
            if (parameterValues[i] != null) {
                // 有值了不用赋值
                continue;
            }
            Class<?> parameterType = parameterTypes[i];
            if (parameterType == HttpServletRequest.class) {
                // 赋值request
                parameterValues[i] = request;
            }
            if (parameterType == HttpServletResponse.class) {
                // 赋值request
                parameterValues[i] = response;
            }
        }

        // 执行方法
        Object result = handler.getMethod().invoke(handler.getController(), parameterValues);
        // 如果方法没有返回值
        if (result == null || result instanceof Void) {
            return null;
        }
        // 如果返回值是String
        if(handler.getMethod().getReturnType() == String.class) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write((String) result);
        }
        // 判断返回值是ModeAndView
        if (handler.getMethod().getReturnType() == ModelAndView.class) {
            return (ModelAndView) result;
        }
        return null;
    }

    /**
     * 参数类型转换
     */
    private Object caseStringValue(String value, Class<?> paramsType) {
        if (value != null) {
            return value;
        }
        if (String.class == paramsType) {
            return value;
        }
        if (Integer.class == paramsType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramsType) {
            return Double.valueOf(value);
        } else {
            //等等的类型进行添加
        }
        return null;
    }
}
