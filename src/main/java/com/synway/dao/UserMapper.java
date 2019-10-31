package com.synway.dao;

import com.synway.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

     User selectById(int id);

     User getUserByLoginInfo(@Param("username")String username,@Param("password")String password);

}
