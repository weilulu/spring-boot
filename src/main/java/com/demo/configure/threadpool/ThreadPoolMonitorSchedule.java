package com.demo.configure.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author weilu
 * @data 9:45 2022/7/6
 */
@Slf4j
@Component
@Async
@ConditionalOnBean(DynamicThreadExecutor.class)
public class ThreadPoolMonitorSchedule {

    @Autowired
    private DynamicThreadExecutor dynamicThreadExecutor;

    @Scheduled(fixedDelay = 2000)
    public void watchThreadPoolInfo(){
        log.info("开始统计线程池相关数据");
        ThreadPoolExecutor threadPoolExecutor = dynamicThreadExecutor.getExecutor();
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        //线程活跃度：活跃线程数趋向于maximumPoolSize的时候，代表线程负载趋高。
        log.info("核心线程数：{},活动线程数：{},最大线程数：{},线程池活跃度：{},任务完成数：{}," +
                 "队列大小：{},当前排队线程数：{},队列剩余大小：{},队列使用度：{}",
                threadPoolExecutor.getCorePoolSize(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getMaximumPoolSize(),
                divide(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize()),
                threadPoolExecutor.getCompletedTaskCount(),
                (queue.size() + queue.remainingCapacity()),
                queue.size(),
                queue.remainingCapacity(),
                divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }


    private String divide(int num1,int num2){
        return String.format("%1.2f%%",Double.parseDouble(num1+"") / Double.parseDouble(num2+""));
    }
}
