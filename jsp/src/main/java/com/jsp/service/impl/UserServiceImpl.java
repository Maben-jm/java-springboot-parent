package com.jsp.service.impl;

import com.jsp.service.UserService;
import com.maben.pojo.UserModel;
import com.jsp.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/12
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserModel getUserModelByUserName(String userName) {
        return userMapper.getUserModelByUserName(userName);
    }
}
