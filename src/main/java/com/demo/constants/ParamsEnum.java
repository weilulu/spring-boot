package com.demo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author weilu
 * @create 2022/6/19
 */
@AllArgsConstructor
public enum ParamsEnum {

    CORE_POOL_SIZE("apollo.async.executor.thread.core_pool_size", "核心线程数"),
    MAXIMUM_POOL_SIZE("dynamic.maximumPoolSize", "最大线程数"),
    KEEP_ALIVE_TIME("dynamic.keepAliveTime", "线程空闲时间"),
    ;

    @Getter
    private String param;

    @Getter
    private String desc;

}
