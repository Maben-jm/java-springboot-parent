package com.jsp.mapper;

import com.maben.pojo.UserModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from t_user where deleted = 0 and user_name = #{userName}")
    UserModel getUserModelByUserName(@Param("userName")String userName);

}
