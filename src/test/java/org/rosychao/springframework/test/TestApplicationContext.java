package org.rosychao.springframework.test;

import org.junit.jupiter.api.Test;
import org.rosychao.springframework.context.ApplicationContext;
import org.rosychao.springframework.context.support.ClassPathXmlApplicationContext;
import org.rosychao.springframework.test.aspect.IAspectService;

public class TestApplicationContext {

    @Test
    public void testClassPathApplicationContext() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:application.properties");
        IAspectService myAction = (IAspectService) ac.getBean("aspectService");
        myAction.test();
        //IQueryService iQueryService = (IQueryService) ac.getBean("org.rosychao.springframework.test.service.IQueryService");
    }

}
