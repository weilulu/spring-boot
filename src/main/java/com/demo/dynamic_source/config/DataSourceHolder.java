package com.demo.dynamic_source.config;

import java.util.Objects;

/**
 * @Author weilu
 * @create 2022/9/27
 */
public class DataSourceHolder {
    public static final ThreadLocal<DataSourceType> SOURCE_HOLDER = new ThreadLocal<DataSourceType>();

    /**
     * 绑定当前线程与数据源
     * @param type
     */
    public static void putDataSourceType(DataSourceType type){
        Objects.requireNonNull(type);
        SOURCE_HOLDER.set(type);
    }

    /**
     * 获取数据源
     * @return
     */
    public static DataSourceType getDataSourceType(){
        return SOURCE_HOLDER.get() == null ? DataSourceType.MASTER : SOURCE_HOLDER.get();
    }

    /**
     * 数据清理
     */
    public static void clearDataSourceType(){
        SOURCE_HOLDER.remove();
    }
}
