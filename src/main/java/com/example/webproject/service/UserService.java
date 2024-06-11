package com.example.webproject.service;

import com.example.webproject.entity.User;
import com.example.webproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    /**
     * 插入用户
     * 用于注册
     * @param user
     */
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    /**
     * 通过用户名查找用户
     * 用于登录
     * @param username 用户名
     * @return userMapper
     */
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}