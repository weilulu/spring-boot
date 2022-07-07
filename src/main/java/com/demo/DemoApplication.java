package com.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author weilu
 * @create 2022/6/7
 */
@MapperScan({"com.demo.dao"})
@SpringBootApplication
@EnableApolloConfig
@EnableScheduling
public class DemoApplication {
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
        //增加输出窗口打印成功信息 方便用户看到启动成功后的标志
        System.out.println("helloworld application success...");
    }
}
