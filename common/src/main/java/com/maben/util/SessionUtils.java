package com.maben.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

/**
 * @author maben
 * @version 1.0
 * @project 党建系统内八
 * @package com.maben.util
 * @file SessionUtils.java 创建时间:2020年06月03日 13:42
 * @title 标题（要求能简洁地表达出类的功能和职责）
 * @description 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @copyright Copyright (c) 2020 中国软件与技术服务股份有限公司
 * @company 中国软件与技术服务股份有限公司
 * @module 模块: 模块名称
 * @reviewer 审核人
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
@Component
public class SessionUtils {
    private static SessionUtils sessionUtils;
    @Autowired
    private RedisTemplate redisTemplateForLog;

    @PostConstruct
    public void init() {
        sessionUtils = this;
        sessionUtils.redisTemplateForLog = this.redisTemplateForLog;
    }

    /**
     * 从缓存中获取对应的用户信息
     */
    public static String getAccessToken() {
        String token = "";
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                token = details.getTokenValue();
                sessionUtils.redisTemplateForLog.expire("String:SESSION:" + token, 30, TimeUnit.MINUTES);
            }
        }
        return token;
    }

    public static String getUserName() {
        String username = "";
        final String token = getAccessToken();
        final String str = (String) sessionUtils.redisTemplateForLog.opsForValue().get("String:SESSION:" + token);
        if (StringUtils.isNotBlank(str)) {
            final JSONObject json = JSONObject.parseObject(str);
            if (json != null) {
                username = json.getString("userName");
            }
        }
        return username;
    }
    public static String getIp() {
        String username = "";
        final String token = getAccessToken();
        final String str = (String) sessionUtils.redisTemplateForLog.opsForValue().get("String:SESSION:" + token);
        if (StringUtils.isNotBlank(str)) {
            final JSONObject json = JSONObject.parseObject(str);
            if (json != null) {
                username = json.getString("ip");
            }
        }
        return username;
    }

}
