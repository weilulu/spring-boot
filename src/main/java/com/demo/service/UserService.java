package com.demo.service;

import com.demo.dao.UserDao;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 下面共演示了三种异步里的事务
 * 第一组：asyncTaskForTransaction，Async与Transactional同时存在一个方法上，事务是生效的
 * 第二组：asyncTaskForTransaction2与test2，子线程里的异常不会影响主线程，所以事务是不生效的
 * 第三组：asyncTaskForTransaction3与test3，异步线程里去调用有事务的方法，事务方法如果出现异常，事务会生效
 * 第四组：asyncTaskForTransaction4与test4，异步线程里去调用有事务的方法，异步线程里出现异常，事务不会生效
 * @Author weilu
 * @create 2022/6/12
 */
@Service
public class UserService {

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private UserDao userDao;

    /**
     * 这样使用事务是生效的，会回滚
     * @param exFlg
     */
    @Async("selfExecutor2")
    @Transactional
    public void asyncTaskForTransaction(Boolean exFlg) {
        User user = new User();
        user.setUserName("tian");
        user.setPassword("98");
        user.setSex("1");
        userDao.addUser(user);
        if (exFlg) {
            throw new RuntimeException("模拟异常");
        }
    }

    /**
     * 子线程抛出了异常，但不会影响到主线程，所以主线程里不会回滚
     * @param exFlg
     */
    @Transactional
    public void asyncTaskForTransaction2(Boolean exFlg) {
        User user = new User();
        user.setUserName("ti");
        user.setPassword("99");
        user.setSex("1");
        userDao.addUser(user);

        applicationContext.getBean(UserService.class).test2(true);
    }

    @Async("selfExecutor2")
    public void test2(Boolean exFlg){
        if (exFlg) {
            throw new RuntimeException("模拟异常");
        }
    }

    @Async("selfExecutor2")
    public void asyncTaskForTransaction3(Boolean exFlg) {
        applicationContext.getBean(UserService.class).test3(true);
    }

    @Transactional
    public void test3(Boolean exFlg){
        User user = new User();
        user.setUserName("tiee");
        user.setPassword("96");
        user.setSex("1");
        userDao.addUser(user);

        if (exFlg) {
            throw new RuntimeException("模拟异常");
        }
    }

    @Async("selfExecutor2")
    public void asyncTaskForTransaction4(Boolean exFlg) {
        applicationContext.getBean(UserService.class).test4(true);
        if (exFlg) {
            throw new RuntimeException("模拟异常");
        }
    }

    @Transactional
    public void test4(Boolean exFlg){
        User user = new User();
        user.setUserName("tiee");
        user.setPassword("96");
        user.setSex("1");
        userDao.addUser(user);
    }
}
