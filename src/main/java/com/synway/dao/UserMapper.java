package com.synway.dao;

import com.synway.pojo.Role;
import com.synway.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

     User selectById(int id);

     User getUserByLoginInfo(@Param("username")String username,@Param("password")String password);

     int saveUser(Map<String,Object> params);

     int saveRole(Map<String,Object> params);

     Role getRoleById(int userId);

     List<User> listUser();

     Map<String,Object> getUserInfoById(int id);

     int updateUser(Map<String,Object> params);

     int delUser(int id);
}
