package com.demo.annotation;

import java.lang.annotation.*;

/**
 * @Author weilu
 * @create 2022/9/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface LoginRequired {
   String sessionKey() default "currentUser";
}
