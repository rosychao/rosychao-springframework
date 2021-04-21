package org.rosychao.springframework.beans.factory.support;

import org.aspectj.lang.annotation.*;
import org.rosychao.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.rosychao.springframework.aop.config.AopConfig;
import org.rosychao.springframework.stereotype.Controller;
import org.rosychao.springframework.stereotype.Service;
import org.rosychao.springframework.util.PathUtils;
import org.rosychao.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * BeanDefinition解析器，适用于properties
 */
public class PropertiesBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private Properties props;

    public PropertiesBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
        props = new Properties();
    }

    public Properties getProps() {
        return props;
    }

    /**
     * 从指定的属性文件加载bean定义
     */
    @Override
    public void loadBeanDefinitions(String location) {
        logger.info("解析配置文件：" + location);
        FileInputStream is = null;
        try {
            is = new FileInputStream(location);
            props.load(is);
            logger.info("解析到的配置有：");
            props.list(System.out);
            // 注册BeanDefinition
            registerBeanDefinitions(props);
        } catch (IOException e) {
            logger.error("获取不到配置文件，请检查location路径！");
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从指定的属性文件加载bean定义
     */
    @Override
    public void loadBeanDefinitions(String... locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 将配置文件注册到BeanDefinitions中
     */
    public void registerBeanDefinitions(Properties prop) {
        logger.info("进行配置解析...");
        // 获取ScanPackage
        String scanPackage = prop.getProperty("scanPackage");
        // 如果有值进行处理
        if (!StringUtils.isEmpty(scanPackage)) {
            logger.info("读取到scanPackage配置，开始扫描包...");
            try {
                doScanPackage(scanPackage);
            } catch (Exception e) {
                logger.info("读取配置文件失败，请检查路径:" + scanPackage);
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描包下的类
     */
    private void doScanPackage(String scanPackage) throws URISyntaxException {
        String url = PathUtils.getClassPathAndPackagePath(scanPackage);
        File classPath = new File(url);
        File[] files = classPath.listFiles();
        if(files == null){
            return;
        }
        logger.info("扫描路径：" + classPath.getAbsolutePath());
        for (File file : files) {
            if (file.isDirectory()) {
                // 如果是目录的话继续递归查找.class
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                // 不是.class的不需要添加
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                // 包名.类名
                String className = (scanPackage + "." + file.getName().replace(".class", ""));
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // 没有添加 Controller和Service注解，不需要添加
                if (!(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class))) {
                    if(clazz.isAnnotationPresent(Aspect.class)){
                        // 说明有AOP配置
                        AopConfig aopConfig = new AopConfig();
                        // 如果配置aspectj-autoproxy，则开启
                        aopConfig.setAspectJAutoProxy(props.get("aspectj-autoproxy") != null ? true : false);
                        aopConfig.setAspectClass(clazz.getName());
                        for (Method method : clazz.getDeclaredMethods()) {
                            // 配置后置通知
                            if (method.isAnnotationPresent(After.class)) {
                                aopConfig.setAspectAfter(method.getName());
                            }
                            // 配置前置通知
                            if (method.isAnnotationPresent(Before.class)) {
                                aopConfig.setAspectBefore(method.getName());
                            }
                            // 配置环绕通知
                            if (method.isAnnotationPresent(Around.class)) {
                                aopConfig.setAspectAround(method.getName());
                            }
                            // 配置异常后通知
                            if (method.isAnnotationPresent(AfterThrowing.class)) {
                                AfterThrowing annotation = method.getAnnotation(AfterThrowing.class);
                                aopConfig.setAspectAfterThrow(method.getName());
                                aopConfig.setAspectAfterThrowingName(annotation.throwing());
                            }
                            // 配置切入点
                            if (method.isAnnotationPresent(Pointcut.class)) {
                                aopConfig.setPointCut(method.getAnnotation(Pointcut.class).value());
                            }
                        }
                        registerAopConfig(aopConfig);
                    }
                    continue;
                }
                // 全限定类名   提供给工厂方法调用的类名
                getRegistry().putBeanDefinitionNames(StringUtils.toLowerFirstCase(file.getName().replace(".class", "")));
                registerBeanDefinition(className, StringUtils.toLowerFirstCase(file.getName().replace(".class", "")));
                registerBeanDefinition(className, className);
                //2、如果是接口，就用实现类
                for (Class<?> i : clazz.getInterfaces()) {
                    registerBeanDefinition(className, i.getName());
                }
            }

        }
    }

    /**
     * 创建BeanDefinition，并加入到BeanDefinitionMap和BeanDefinitionNames中
     */
    protected void registerBeanDefinition(String className, String factoryBeanName) {
        // 创建BeanDefinition;
        AbstractBeanDefinition bd = BeanDefinitionReaderUtils.createBeanDefinition(className, factoryBeanName);
        // 立即加载初始化
        bd.setLazyInit(false);
        getRegistry().registerBeanDefinition(className, bd);
        logger.info("扫描到类文件，并添加【" + className + "】到BeanDefinition容器中...");
    }

    /**
     * 注册AOP配置文件
     */
    protected void registerAopConfig(AopConfig aopConfig){
        AnnotationAwareAspectJAutoProxyCreator abstractAutoProxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        abstractAutoProxyCreator.setAopConfig(aopConfig);
        getRegistry().addBeanPostProcessor(abstractAutoProxyCreator);
    }

}
