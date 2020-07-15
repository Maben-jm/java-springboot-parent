package com.example.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 
 *
 * @project 党建系统
 * @package css.djxt.oauth.service
 * @file RedisAuthorizationCodeServices.java 创建时间:2019年2月21日下午11:53:07
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
@Service
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 存储code到redis，并设置过期时间，60分钟<br>
     * value为OAuth2Authentication序列化后的字节<br>
     * 因为OAuth2Authentication没有无参构造函数<br>
     * redisTemplate.opsForValue().set(key, value, timeout, unit);
     * 这种方式直接存储的话，redisTemplate.opsForValue().get(key)的时候有些问题，
     * 所以这里采用最底层的方式存储，get的时候也用最底层的方式获取
     * 
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            String sxcs = "30";
            connection.set(codeKey(code).getBytes(), SerializationUtils.serialize(authentication), Expiration.from(Long.parseLong(sxcs), TimeUnit.MINUTES), SetOption.UPSERT);
            return 1L;
        });
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年2月21日下午11:53:37
     *@param code code
     *@return OAuth2Authentication
     * @author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @Override
    protected OAuth2Authentication remove(final String code) {
        final OAuth2Authentication oAuth2Authentication = redisTemplate.execute(new RedisCallback<OAuth2Authentication>() {

            @Override
            public OAuth2Authentication doInRedis(RedisConnection connection) throws DataAccessException {
                final byte[] keyByte = codeKey(code).getBytes();
                final byte[] valueByte = connection.get(keyByte);

                if (valueByte != null) {
                    connection.del(keyByte);
                    return SerializationUtils.deserialize(valueByte);
                }

                return null;
            }
        });

        return oAuth2Authentication;
    }

    /**
     * 拼装redis中key的前缀
     * 
     * @param code code
     * @return String
     */
    private String codeKey(String code) {
        return "oauth2:codes:" + code;
    }
}
