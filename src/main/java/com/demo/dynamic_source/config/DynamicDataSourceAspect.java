package com.demo.dynamic_source.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.demo.dynamic_source.config.DataSourceType.MASTER;
import static com.demo.dynamic_source.config.DataSourceType.SLAVE;

/**
 * @Author weilu
 * @create 2022/9/27
 *
 * 原理：根据自定义注解选择数据源，然后使用AOP将选择的数据源放到ThreadLocal里，
 * 最后在设置数据的地方使用spring AbstractRoutingDataSource的方法从threadLocal里取出
 * 相应的数据源进行使用，使用完之后中再将数据源进行清理(这也是在AOP里完成的)
 * 注意，启动类上需要加EnableTransactionManagement注解
 *
 * 参考的文章：
 * https://www.cnblogs.com/youzhibing/p/11965210.html
 * https://gitee.com/youzhibing/spring-boot-2.0.3/tree/master/spring-boot-dynamic-DataSource
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class DynamicDataSourceAspect {

    @Pointcut("execution(public * com.demo.dynamic_source.service.*.*(..))")
    public void dynamicExecute(){}

    @Pointcut("execution(public * com.demo.dynamic_source.config.MasterSlave")
    public void dynamicAnnotation(){}

    @Before("dynamicAnnotation()&&dynamicExecute()&&@annotation(masterSlave)")
    public void putDataSource(JoinPoint point,MasterSlave masterSlave){
        DataSourceType value = masterSlave.value();
        if(MASTER.equals(value)){
            DataSourceHolder.putDataSourceType(MASTER);
        }else if(SLAVE.equals(value)){
            DataSourceHolder.putDataSourceType(SLAVE);
        }else{
            DataSourceHolder.putDataSourceType(MASTER);
        }
        log.info("put datasource successfully");
    }

    @After("dynamicAnnotation()&&dynamicExecute()&&@annotation(masterSlave)")
    public void clearDataSource(JoinPoint point,MasterSlave masterSlave){
        DataSourceHolder.clearDataSourceType();
        log.info("clear datasource successfully");
    }
}
