package org.rosychao.springframework.test.aspect;

import org.rosychao.springframework.stereotype.Service;

@Service
public class AspectService implements IAspectService {

    @Override
    public void test() throws Exception {
        System.out.println("测试中....");
        throw new NullPointerException("空指针异常啦~");
    }


}
