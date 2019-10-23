package com.synway.service;

import com.synway.dao.RoleMapper;
import com.synway.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role getRoleAndPermission(int id){
         Role role = roleMapper.getRoleAndPermission(id);
         return role;
    }

}
