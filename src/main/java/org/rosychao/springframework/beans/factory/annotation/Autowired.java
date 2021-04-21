package org.rosychao.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 用于注入Bean，控制层
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
