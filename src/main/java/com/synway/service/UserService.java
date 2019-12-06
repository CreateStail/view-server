package com.synway.service;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.synway.dao.UserMapper;
import com.synway.pojo.Role;
import com.synway.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    private static final Gson gson = new Gson();

    public User selectById(int id) {
        User user = userMapper.selectById(id);
        return user;
    }

    public User getUserByLoginInfo(String username, String password) {
        User user = userMapper.getUserByLoginInfo(username, SecureUtil.md5(password));
        return user;
    }

    public Role getRoleById(int userId) {
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

    public Map<String,Object> listUser(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        PageHelper.startPage(Integer.parseInt(String.valueOf(params.get("page"))), Integer.parseInt(String.valueOf(params.get("pageSize"))),
                params.get("sort") + " " + params.get("order"));
        List<User> list = userMapper.listUser();
        PageInfo pageInfo = new PageInfo(list);
        resultMap.put("total", pageInfo.getTotal());
        resultMap.put("rows", pageInfo.getList());
        return resultMap;
    }


}
