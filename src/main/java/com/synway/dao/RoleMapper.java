package com.synway.dao;

import com.synway.pojo.Role;

public interface RoleMapper {
    Role getRoleAndPermission(int id);
}
