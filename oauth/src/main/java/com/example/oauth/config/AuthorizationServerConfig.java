package com.example.oauth.config;

import com.example.oauth.service.impl.RedisAuthorizationCodeServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 添加认证的配置类，使用Redis用来存储token
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * redisConnectionFactory
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * dataSource
     */
    @Autowired
    private MultiDataSourceConfig dataSource;

    /**
     * redisAuthorizationCodeServices
     */
    @Autowired
    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenStore(tokenStore());
        // 授权码模式下，code存储
        endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
    }

    /**
     * 这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
     * 如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        final DruidDataSource datasource2 = dataSource.primaryDataSource();
//        clients.jdbc(datasource2);
        String finalSecret = new BCryptPasswordEncoder().encode("123321");
        // 配置两个客户端，一个用于password认证，一个用于client认证
        clients.inMemory().withClient("system_client")
            .resourceIds(ResourceServerConfig.DEMO_RESOURCE_ID)
            .authorizedGrantTypes("client_credentials")
            .scopes("select")
            .authorities("oauth2")
            .secret(finalSecret)
            .and()
            .withClient("system_pass")
            .resourceIds(ResourceServerConfig.DEMO_RESOURCE_ID)
            .authorizedGrantTypes("password", "refresh_token")
            .scopes("select")
            .authorities("oauth2")
            .secret(finalSecret);
    }
}
