package com.example.oauth.service.impl;

import com.example.oauth.mapper.UserMapper;
import com.example.oauth.pojo.LoginAppUser;
import com.maben.pojo.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userMapper.findByUserName(username);
        LoginAppUser loginAppUser = new LoginAppUser();
        loginAppUser.setUserName(user.getUserName());
        loginAppUser.setPassword(user.getPassword());
        return loginAppUser;
    }
}