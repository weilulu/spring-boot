package com.demo.controller;

import com.demo.configure.threadpool.DynamicThreadExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author weilu
 * @create 2022/6/18
 * 动态调整线程池参数
 */
@RestController
public class DynamicThreadPoolController {

    @Resource
    private DynamicThreadExecutor dynamicThreadExecutor;

    @RequestMapping("/dynamicThreadPoolConfig")
    public String dynamicThreadPoolConfig(){
        while (true) {
            dynamicThreadExecutor.execute( new Runnable() {
                @Override
                public void run() {
                    System.out.println("bizInfo");
                }
            });
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                //todo
            }
        }
    }

}
