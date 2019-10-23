package com.synway.service;

import com.synway.dao.UserMapper;
import com.synway.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(int id);

}
