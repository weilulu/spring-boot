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

    @Resource(name = "selfExecutor1")
    private ThreadPoolTaskExecutor selfExecutor1;


    @RequestMapping("/dynamicThreadPoolConfig")
    public String dynamicThreadPoolConfig(){
        while (true) {
            dynamicThreadExecutor.execute("bizName", new Runnable() {
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
    @RequestMapping("/getThreadPoolInfo")
    public String getThreadPoolInfo() {
        if(selfExecutor1 != null){
            ThreadPoolExecutor threadPoolExecutor = dynamicThreadExecutor.getExecutor("test");
            int corePoolSize = threadPoolExecutor.getCorePoolSize();
            int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
            int poolSize = threadPoolExecutor.getPoolSize();
            System.out.println("核心线程数:"+corePoolSize+",最大线程数:"+maximumPoolSize+",队列大小："+poolSize);
            BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
            System.out.println(Thread.currentThread().getName() + "-:" +
                    "核心线程数：" + threadPoolExecutor.getCorePoolSize() +
                    " 活动线程数：" + threadPoolExecutor.getActiveCount() +
                    " 最大线程数：" + threadPoolExecutor.getMaximumPoolSize() +
                    " 线程池活跃度：" + divide(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize()) +
                    " 任务完成数：" + threadPoolExecutor.getCompletedTaskCount() +
                    " 队列大小：" + (queue.size() + queue.remainingCapacity()) +
                    " 当前排队线程数：" + queue.size() +
                    " 队列剩余大小：" + queue.remainingCapacity() +
                    " 队列使用度：" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
        }
        return "test";
    }
    private String divide(int num1,int num2){
        return String.format("%1.2f%%",Double.parseDouble(num1+"") / Double.parseDouble(num2+""));
    }
}
