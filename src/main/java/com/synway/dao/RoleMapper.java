package com.synway.dao;

import com.synway.domain.Role;

/**
 * Created by issuser on 2019/10/23.
 */
public interface RoleMapper {
    Role getRoleAndPermission(int id);
}
