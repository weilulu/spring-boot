package com.demo.configure.threadpool;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.demo.constants.ParamsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @Author weilu
 * @create 2022/6/19
 */
@Slf4j
@Component
public class DynamicThreadPoolConfig {
    /** apollo里的namespace **/
    private static final String NAME_SPACE = "lepu-activity-center";

    /** 线程执行器 **/
    private volatile ThreadPoolExecutor executor;

    /** 核心线程数 **/
    private Integer corePoolSize = 10;

    /** 最大值线程数 **/
    private Integer maximumPoolSize = 20;

    /** 待执行任务的队列的长度 **/
    private Integer workQueueSize = 1000;

    /** 线程空闲时间 **/
    private Long keepAliveTime = 1000L;

    /** 线程名 **/
    private String threadName;

    private Config config = ConfigService.getConfig("lepu-activity-center");;

    public DynamicThreadPoolConfig() {

        //Config config = ConfigService.getConfig(NAME_SPACE);
        init(config);
        //listen(config);
    }

    /** * 初始化 */
    private void init(Config config) {
        log.info("线程池初始化中..........");
        if (executor == null) {

            synchronized (DynamicThreadPoolConfig.class) {

                if (executor == null) {

                    String corePoolSizeProperty = config.getProperty("corePoolSize", corePoolSize.toString());
                    log.info("修改前的核心线程池:{}",corePoolSizeProperty);
                    String maximumPoolSizeProperty = config.getProperty("maximumPoolSize", maximumPoolSize.toString());
                    String keepAliveTImeProperty = config.getProperty("keepAliveTime", keepAliveTime.toString());
                    BlockingQueue<Runnable> workQueueProperty = new LinkedBlockingQueue<>(workQueueSize);
                    executor = new ThreadPoolExecutor(Integer.valueOf(corePoolSizeProperty), Integer.valueOf(maximumPoolSizeProperty),
                            Long.valueOf(keepAliveTImeProperty), TimeUnit.MILLISECONDS, workQueueProperty);
                }
            }
        }
    }

    /**
     * 监听到配置中心发生变化后，更新线程池配置
     * @param changeEvent
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent){
        log.info("线程池参数配置发生变化,namespace:{}",changeEvent.getNamespace());
            for(String key : changeEvent.changedKeys()){
                ConfigChange change = changeEvent.getChange(key);
                String newValue = change.getNewValue();
                refreshThreadPool(key,newValue);
            }
    }

    /**
     * 更新线程池配置
     * @param key
     * @param newValue
     */
    private void refreshThreadPool(String key, String newValue) {

        if (executor == null) {

            return;
        }
        if (ParamsEnum.CORE_POOL_SIZE.getParam().equals(key)) {

            executor.setCorePoolSize(Integer.valueOf(newValue));
            log.info("修改核心线程数key={},value={}",key,newValue);
        }
        if (ParamsEnum.MAXIMUM_POOL_SIZE.getParam().equals(key)) {

            executor.setMaximumPoolSize(Integer.valueOf(newValue));
            log.info("修改最大线程数key={},value={}", key, newValue);
        }
        if (ParamsEnum.KEEP_ALIVE_TIME.getParam().equals(key)) {

            executor.setKeepAliveTime(Integer.valueOf(newValue), TimeUnit.MILLISECONDS);
            log.info("修改线程空闲时间key={},value={}", key, newValue);
        }
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
