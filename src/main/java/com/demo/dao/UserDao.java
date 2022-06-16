package com.demo.dao;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author weilu
 * @create 2022/6/12
 */
@Mapper
public interface UserDao {
    void addUser(User user);
}
