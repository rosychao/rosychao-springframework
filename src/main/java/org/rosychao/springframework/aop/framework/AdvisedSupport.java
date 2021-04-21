package org.rosychao.springframework.aop.framework;

import com.sun.istack.internal.Nullable;
import org.rosychao.springframework.aop.aspectj.AspectJAfterAdvice;
import org.rosychao.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import org.rosychao.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.rosychao.springframework.aop.config.AopConfig;
import org.rosychao.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AOP代理配置管理器的基类，实现AOP链的调用
 */
public class AdvisedSupport {

    /**
     * 被代理的目标类
     */
    private Class<?> targetClass;
    /**
     * 被代理的目标实例对象
     */
    private Object target;
    /**
     * AOP配置文件
     */
    private AopConfig config;
    /**
     * 正则字符匹配
     */
    private Pattern pointCutClassPattern;
    /**
     * 存放方法和增强通知的集合
     */
    private transient Map<Method, List<Object>> methodCache = new HashMap();

    public AdvisedSupport(AopConfig config) {
        this.config = config;
        setPointCutClassPattern();
    }

    private void setPointCutClassPattern() {
        String pointCut = config.getPointCut();
        // pointCut：public .* org.rosychao.springframework.test.service..*Service..*(.*)
        // 正则表达式转换
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        this.pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        try {
            this.targetClass = Class.forName(targetClass);
            initMethodCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化，建立方法与通知关系集合
     */
    private void initMethodCache() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pattern pattern = Pattern.compile(config.getPointCut());
        Class aspectClass = Class.forName(this.config.getAspectClass());

        Map<String, Method> aspectMethods = new HashMap();

        for (Method m : aspectClass.getMethods()) {
            aspectMethods.put(m.getName(), m);
        }

        for (Method m : this.targetClass.getMethods()) {
            String methodString = m.toString();
            if (methodString.contains("throws")) {
                methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
            }

            Matcher matcher = pattern.matcher(methodString);
            if (matcher.matches()) {
                // 执行器链
                List<Object> advices = new LinkedList();
                // 把每一个方法包装成 MethodInterceptor
                // before
                if (!StringUtils.isEmpty(config.getAspectBefore())) {
                    // 创建一个Advice
                    advices.add(new AspectJMethodBeforeAdvice(aspectMethods.get(config.getAspectBefore()), aspectClass.newInstance()));
                }
                // Around
                /*if (!StringUtils.isEmpty(config.getAspectAround())) {
                    // 创建一个Advice
                    advices.add(new AspectJAroundAdvice(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                }*/
                // after
                if (!StringUtils.isEmpty(config.getAspectAfter())) {
                    // 创建一个Advice
                    advices.add(new AspectJAfterAdvice(aspectMethods.get(config.getAspectAfter()), aspectClass.newInstance()));
                }
                // afterThrowing
                if (!StringUtils.isEmpty(config.getAspectAfterThrow())) {
                    // 创建一个Advice
                    AspectJAfterThrowingAdvice throwingAdvice =
                            new AspectJAfterThrowingAdvice(
                                    aspectMethods.get(config.getAspectAfterThrow()),
                                    aspectClass.newInstance());
                    throwingAdvice.setThrowingName(config.getAspectAfterThrowingName());
                    advices.add(throwingAdvice);
                }
                // 生成好的执行器链与方法建立联系
                methodCache.put(m, advices);
            }

        }

    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * 匹配表达式
     */
    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    /**
     * 根据此配置，确定给定方法的执行器链
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, @Nullable Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cached = this.methodCache.get(method);
        if (cached == null) {
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cached = methodCache.get(m);
            // 对代理方法进行一个兼容处理
            this.methodCache.put(m,cached);
        }
        return cached;
    }

}
