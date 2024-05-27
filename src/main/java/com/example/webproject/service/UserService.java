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

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}