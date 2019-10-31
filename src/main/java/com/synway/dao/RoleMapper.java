package com.synway.dao;

import com.synway.pojo.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    Role getRoleAndPermission(int id);
}
