package com.synway.service;

import cn.hutool.crypto.SecureUtil;
import com.synway.dao.UserMapper;
import com.synway.pojo.Role;
import com.synway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(int id) {
        User user = userMapper.selectById(id);
        return user;
    }

    public User getUserByLoginInfo(String username, String password) {
        User user = userMapper.getUserByLoginInfo(username, SecureUtil.md5(password));
        return user;
    }

    public Role getRoleById(int userId){
        Role role = userMapper.getRoleById(userId);
        return role;
    }

    @Transactional
    public boolean saveUser(Map<String, Object> params) {
        params.put("userId", null);
        params.put("role", "user");
        params.put("permission", "view");
        int saveUserResult = userMapper.saveUser(params);
        int saveRoleResult = 0;
        if (params.get("userId") != null) {
            saveRoleResult = userMapper.saveRole(params);
        }
        if (saveUserResult > 0 && saveRoleResult > 0) {
            return true;
        } else {
            return false;
        }
    }


}
