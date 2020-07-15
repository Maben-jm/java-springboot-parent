package com.example.oauth.mapper;

import com.maben.pojo.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM t_user WHERE deleted=0 and user_name = #{username}")
    UserModel findByUserName(@Param("username") String username);
}