package com.demo.configure.threadpool;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @Author weilu
 * @create 2022/6/19
 */
@Component
public class DynamicThreadExecutor {
    @Resource
    private DynamicThreadPoolConfig threadPoolFactory;

    public void execute(String bizName, Runnable job) {

        threadPoolFactory.getExecutor(bizName).execute(job);
    }

    public Future<?> submit(String bizName, Runnable job) {

        return threadPoolFactory.getExecutor(bizName).submit(job);
    }
}
