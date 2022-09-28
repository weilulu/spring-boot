package com.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.demo.configure.login.LoginRequiredArgumentResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author weilu
 * @create 2022/6/7
 */
@MapperScan({"com.demo.dao"})
@SpringBootApplication
@EnableApolloConfig
@EnableScheduling
public class DemoApplication implements WebMvcConfigurer{
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
        //增加输出窗口打印成功信息 方便用户看到启动成功后的标志
        System.out.println("helloworld application success...");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new LoginRequiredArgumentResolver());
    }
}
