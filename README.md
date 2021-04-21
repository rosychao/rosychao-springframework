# rosychao-springframework

简介：本项目是在学习Spring源码时，通过高仿SpringFramework 5的顶层类设计，实现在自己的Spring框架，具有IOC、AOP、WEB功能模块

根据平时使用Spring的方式，找到分析源码的入口，本项目也是通过两个入口一步一步参照Spring顶层设计去完成
    
如果想运行本项目实现效果，或者看运行流程，参照test下的代码，也是通过两个入口去探究

### IOC入口：
mian方法：
~~~java
ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application.properties");
IAspectService myAction = (IAspectService) ac.getBean("aspectService");
myAction.test();
IQueryService iQueryService = (IQueryService) ac.getBean("org.rosychao.springframework.test.service.IQueryService");
~~~

### WEB入口：
配置xml:
~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
		  http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
           version="2.5">

    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.rosychao.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:application.properties</param-value>
        </init-param>

        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>

</web-app>
~~~
使用@Controller、@RequestMapping来注入bean，解析接口路径映射关系

### 约定大于配置
为了简化项目开发，采用properties配置文件，俗话说约定大于配置，本项目主要注重功能流程的实现，很多地方没校验有缺陷，请遵守基本Spring开发约定
~~~
# 配置扫描的包路径
scanPackage=org.rosychao.springframework.test

# 模板存放路径
templatePath=template

# 开启Aspect
aspectj-autoproxy=true
~~~

### 配置AOP：
~~~
@Aspect
public class AspectAopConfig {

    @Pointcut("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void test(){

    }

    @Before("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testBefore(){
        System.out.println("...before...");
    }

    @After("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testAfter(){
        System.out.println("...after...");
    }

    @Around("public .* org.rosychao.springframework.test.aspect..*Service..*(.*)")
    public void testAround(){
        System.out.println("...around...");
    }

    @AfterThrowing(throwing="java.lang.Exception")
    public void testAfterThrowing(Throwable ex){
        System.out.println("...异常：" + ex.getMessage() + "...");
    }
}
~~~
要位于扫描包路径下，使用@Aspect注解加入IOC容器，并根据切入点匹配生成代理对象。

### UML架构图
![架构图](https://raw.githubusercontent.com/rosychao/rosychao-springframework/81f2c4c6e72e49386a4f6e8b3de8c0c80e589894/UML%E6%9E%B6%E6%9E%84%E5%9B%BE.png)
仿照Spring的顶层设计来的

参考资料：
SpringFramework源码
《Spring源码深度解析（第2版）》
《Spring5核心原理与30个类手写实战》
各论坛网站
