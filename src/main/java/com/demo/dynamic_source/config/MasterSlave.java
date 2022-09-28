package com.demo.dynamic_source.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author weilu
 * @create 2022/9/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MasterSlave {

    DataSourceType value() default  DataSourceType.MASTER;
}
