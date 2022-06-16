package com.demo.controller;

import com.demo.service.ThreadExecutorService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author weilu
 * @create 2022/6/9
 */
@RestController
public class TransactionalTestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test_transaction_suc1")
    public String test() {
        userService.asyncTaskForTransaction(true);
        return "test";
    }

    @RequestMapping("/test_transaction_fail")
    public String testFail() {
        userService.asyncTaskForTransaction2(true);
        return "test";
    }

    @RequestMapping("/test_transaction_suc2")
    public String testSuc() {
        userService.asyncTaskForTransaction3(true);
        return "test";
    }

}
