package com.demo.controller;

import com.demo.annotation.LoginRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author weilu
 * @create 2022/9/5
 * 统一登录进行集中判断，好像没生效。。。
 * 参考：https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/Java%20%E4%B8%9A%E5%8A%A1%E5%BC%80%E5%8F%91%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF%20100%20%E4%BE%8B/27%20%E6%95%B0%E6%8D%AE%E6%BA%90%E5%A4%B4%EF%BC%9A%E4%BB%BB%E4%BD%95%E5%AE%A2%E6%88%B7%E7%AB%AF%E7%9A%84%E4%B8%9C%E8%A5%BF%E9%83%BD%E4%B8%8D%E5%8F%AF%E4%BF%A1%E4%BB%BB.md
 */

@RestController
public class NeedLoginController {
    @GetMapping("login")
    public String login(@LoginRequired @RequestParam("userId") Long userId){
        return "当前用户："+userId;
    }
}
