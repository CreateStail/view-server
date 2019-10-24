package com.synway.service;

import com.synway.dao.UserMapper;
import com.synway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(int id){
        User user = userMapper.selectById(id);
        return user;
    }

    public User getUserByLoginInfo(String username,String password){
        User user = userMapper.getUserByLoginInfo(username,password);
        return user;
    }

}
