package com.example.oauth.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.concurrent.TimeUnit;

/**
 * @project 党建系统
 * @package css.djxt.oauth.ctrl
 * @file OAuth2Controller.java 创建时间:2019年2月15日下午12:08:10
 * @title 标题（要求能简洁地表达出类的功能和职责）
 * @description 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @copyright Copyright (c) 2019 中国软件与技术服务股份有限公司
 * @company 中国软件与技术服务股份有限公司
 * @module 模块: 模块名称
 * @author   史雪涛
 * @reviewer 审核人
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 *
 */
@RestController
@RequestMapping
public class OAuth2Controller {

    /**
     * 
     */
    private static Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);
    /**
     * tokenStore
     */
    @Autowired
    private TokenStore tokenStore;
    

    /**
     * 登陆用户信息
     * 
     * @param principal principal
     * @return Principal 
     * @throws Exception 
     */
    @GetMapping("/check_token")
    public Principal principal(Principal principal) throws Exception {
        logger.info("principal=="+principal);
        String sxcs = "1800";
        final String accessToken = getAccessToken();
        if(StringUtils.isBlank(accessToken)){
            redisTemplate.expire("String:SESSION:" + accessToken, Long.parseLong(sxcs),TimeUnit.SECONDS);// 超时时间1小时
        }
        return principal;
    }

    @Autowired
    RedisTemplate redisTemplate;
    /**
     *
     * @return String
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String token = "";
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                token = details.getTokenValue();
                String sxcs = "1800";
                redisTemplate.expire("String:SESSION:" + token, Long.parseLong(sxcs), TimeUnit.SECONDS);
            }
        }
        return token;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年2月15日下午12:08:28
     *@param principal principal
     *@param accessToken accessToken
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @DeleteMapping(value = "/remove_token", params = "accessToken")
    public void removeToken(Principal principal, String accessToken) {
        final OAuth2AccessToken accessToken1 = tokenStore.readAccessToken(accessToken);
        if (accessToken1 != null) {
            // 移除access_token
            tokenStore.removeAccessToken(accessToken1);

            // 移除refresh_token
            if (accessToken1.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(accessToken1.getRefreshToken());
            }
        }
    }
}
