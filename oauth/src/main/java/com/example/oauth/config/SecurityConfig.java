package com.example.oauth.config;

import com.example.oauth.pojo.PermitAllUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * userDetailsService
     */
    @Autowired
    public UserDetailsService userDetailsService;

    /**
     *
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年2月15日下午12:05:56
     *@return BCryptPasswordEncoder
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * 全局用户信息
     *
     * @param auth auth
     *            认证管理
     * @throws Exception
     *             用户认证异常信息
     */
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 认证管理
     *
     * @return 认证管理对象
     * @throws Exception
     *             认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * http安全配置
     *
     * @param http
     *            http安全对象
     * @throws Exception
     *             http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl()).permitAll().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
    }

}
