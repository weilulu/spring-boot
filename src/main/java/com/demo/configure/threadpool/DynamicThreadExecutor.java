package com.demo.configure.threadpool;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author weilu
 * @create 2022/6/19
 */
@Component
public class DynamicThreadExecutor {
    @Resource
    private DynamicThreadPoolConfig threadPoolFactory;

    public ThreadPoolExecutor getExecutor(){
        return threadPoolFactory.getExecutor();
    }
    public void execute(Runnable job) {

        threadPoolFactory.getExecutor().execute(job);
    }

    public Future<?> submit(Runnable job) {

        return threadPoolFactory.getExecutor().submit(job);
    }
}
