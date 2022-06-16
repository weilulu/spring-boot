package com.demo.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author weilu
 * @create 2022/6/7
 *
 * 1，定义不同的线程池，做到线程池之间隔离
 * 2，可以自定义拒绝策略，但不能自定义异常处理器
 * 3，实现AsyncConfigurer与继承AsyncConfigurerSupport可以自定义异常处理器
 */
@Configuration
@EnableAsync
public class SelfTaskExecutorConfig1 {
    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds;
    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;

    @Bean(name = "selfExecutor1")
    public Executor selfExecutor1(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //设置缓冲队列大小
        executor.setQueueCapacity(queueCapacity);
        //设置线程的最大空闲时间，超过了核心线程数之外的线程，在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池关闭时等待任务执行完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //承接上一参数，等待时长，如果任务未执行完成达到等待时长后强制关闭
        executor.setAwaitTerminationSeconds(60);
        //设置线程名前缀
        executor.setThreadNamePrefix(namePrefix+"1-");
        // 线程池初始化
        executor.initialize();

        return executor;
    }

    @Bean(name = "selfExecutor2")
    public Executor selfExecutor2(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //设置缓冲队列大小
        executor.setQueueCapacity(queueCapacity);
        //设置线程的最大空闲时间，超过了核心线程数之外的线程，在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //设置拒绝策略，也可以实现RejectedExecutionHandler进行自定义
        executor.setRejectedExecutionHandler(new SelfRejectHandle());
        //线程池关闭时等待任务执行完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //承接上一参数，等待时长，如果任务未执行完成达到等待时长后强制关闭
        executor.setAwaitTerminationSeconds(60);
        //设置线程名前缀
        executor.setThreadNamePrefix(namePrefix+"2-");


        // 线程池初始化
        executor.initialize();

        return executor;
    }

    /**
     * 自定义拒绝策略
     */
    class SelfRejectHandle implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }
}
