package com.example.oauth.config;

import com.example.oauth.pojo.PermitAllUrl;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    static final String DEMO_RESOURCE_ID = "order";
    private static String[] urls = {
        "/loadUserByUsername/**",
        "/favicon.ico",
        "/Mh001LoginCtrl/**",
        "/login/**",
        "/handleLogins/**",
        "/css/**",
        "/wings/**",
        "/wingWeb/**",
        "/images/**",
        "/fonts/**",
        "/img/**",
        "/*.html",
        "/*.jsp",
        "/**/*.js",
        "/**/*.ico",
        "/test/**",
        "/error/**"
    };

    /**
     * 报错跳转
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
    }

    /**
     * @param http http
     * @name 中文名称
     * @description 相关说明
     * @time 创建时间:2019年2月15日下午12:05:28
     * @author 史雪涛
     * @history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        final String[] allUrl = PermitAllUrl.permitAllUrl(urls);

        http
            .csrf().disable().exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint())
            .and()
            .requestMatchers().anyRequest()
            .and()
            .anonymous()
            .and()
            .authorizeRequests()
            .antMatchers(allUrl)
            .permitAll()
            .anyRequest()
            .authenticated();

    }

    /**
     * 判断来源请求是否包含oauth2授权信息
     */
    private static class OAuth2RequestedMatcher implements RequestMatcher {
        /**
         * @param request request
         * @return request
         * @name 中文名称
         * @description 相关说明
         * @time 创建时间:2019年2月15日下午12:05:20
         * @author 史雪涛
         * @history 修订历史（历次修订内容、修订人、修订时间等）
         */
        @Override
        public boolean matches(HttpServletRequest request) {
            // 请求参数中包含access_token参数
            if (request.getParameter(OAuth2AccessToken.ACCESS_TOKEN) != null) {
                return true;
            }

            // 头部的Authorization值以Bearer开头
            final String auth = request.getHeader("Authorization");
            if (auth != null) {
                return auth.startsWith(OAuth2AccessToken.BEARER_TYPE);
            }


            return false;
        }
    }
}


