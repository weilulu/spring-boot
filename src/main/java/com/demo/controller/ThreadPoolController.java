package com.demo.controller;

import com.demo.service.ThreadExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author weilu
 * @create 2022/6/9
 */
@RestController
public class ThreadPoolController {

    @Autowired
    private ThreadExecutorService threadExecutorService;

    @RequestMapping("/test")
    public String test() {
        for(int i=0;i<30;i++) {
            threadExecutorService.test();
        }
        return "test";
    }

    @RequestMapping("testResponse")
    public String tesResponse(){
        String value = "test";
        /*Future<String> future = threadExecutorService.testResponse(value);
        String response = null;
        try {
            response = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        //如果直接返回string,这里是拿不到结果的。必须返回future才行
        String response = threadExecutorService.testResponse2(value);
        System.out.println("response:"+response);
        return "test";
    }

    /**
     * 测试异常捕获，如果直接在future = threadExecutorService.testException(flag);上捕获异常是取不取异常信息的，
     * 必须在future.get()的时候进行捕获
     * @return
     */
    @RequestMapping("testFutureException")
    public String testFutureException(){
        Boolean flag = true;
        //如果直接返回string,这里是拿不到结果的。必须返回future才行
        Future<String> future = null;
        future = threadExecutorService.testFutureException(flag);
        String result = "";
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("future get exception:"+e.getMessage());
        }
        System.out.println("response:"+result);
        return "test";
    }

    /**
     * 测试在没有返回时发生异常的情形
     * 直接在外层也是拿不到异常信息的，只能在线程池里进行异常捕获
     * @return
     */
    @RequestMapping("testVoidException")
    public String testVoidException(){
        Boolean flag = true;
        for(int i=0;i<5;i++) {
            try {
                threadExecutorService.testVoidException(flag);
            } catch (Exception e) {//这样是捕获不到异常的，得在线程池里捕获
                System.out.println("threadpool exception:"+e.getMessage());
            }
        }
        return "test";
    }
}
