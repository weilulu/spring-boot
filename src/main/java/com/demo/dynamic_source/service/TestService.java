package com.demo.dynamic_source.service;

import com.demo.dynamic_source.config.MasterSlave;
import org.springframework.stereotype.Service;

import static com.demo.dynamic_source.config.DataSourceType.MASTER;

/**
 * @Author weilu
 * @create 2022/9/27
 */
@Service
public class TestService {

    @MasterSlave(MASTER)
    public String test(){
        return "test";
    }
}
