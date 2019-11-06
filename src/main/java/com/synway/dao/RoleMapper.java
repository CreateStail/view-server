package com.synway.dao;

import com.synway.domain.JWTToken;
import com.synway.pojo.Introduction;
import com.synway.pojo.Role;
import org.springframework.stereotype.Repository;

import java.beans.Customizer;

@Repository
public interface RoleMapper  {
    Role getRoleAndPermission(int id);
}
