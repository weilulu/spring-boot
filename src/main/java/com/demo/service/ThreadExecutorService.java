package com.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author weilu
 * @create 2022/6/7
 */
@Service
public class ThreadExecutorService {

    @Async("selfExecutor1")
    public  void test(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            //不处理中断异常
        }
        System.out.println(Thread.currentThread().getName()+" thread pool test");
    }
    public void test2(){
        test();
    }

    @Async("selfExecutor1")
    public Future<String> testResponse(String value){
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            //不处理中断异常
        }
        return AsyncResult.forValue(value);
    }
    @Async("selfExecutor1")
    public String testResponse2(String value){
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            //不处理中断异常
        }
        return value;
    }

    @Async("selfExecutor2")
    public Future<String> testFutureException(Boolean flag)   {
        if(flag){
            int a = 2/0;
        }
        return AsyncResult.forValue("testFutureException");
    }

    @Async("selfExecutor2")
    public void testVoidException(Boolean flag)   {
        System.out.println(Thread.currentThread().getName()+" thread pool test");

        if(flag){
            try {
                int a = 2/0;
            }catch (Exception e){
                System.out.println("运行时出现异常了,message:"+e.getMessage());
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            //ingore
        }
    }
}
