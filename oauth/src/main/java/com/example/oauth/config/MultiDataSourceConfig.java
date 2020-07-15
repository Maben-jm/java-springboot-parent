package com.example.oauth.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @project 党建系统
 * @package css.djxt.xtgl.config
 * @file MultiDataSourceConfig.java 创建时间:2019年4月10日上午10:23:23
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
@Configuration
public class MultiDataSourceConfig {

    /**
     * 
     */
    @Value("${spring.datasource.url}")
    private String dbUrl;
    /**
     * 
     */
    @Value("${spring.datasource.username}")
    private String username;
    /**
     * 
     */
    @Value("${spring.datasource.password}")
    private String password;
    /**
     * 
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    /**
     * 
     */
    @Value("${spring.datasource.min-idle}")
    private int minIdle;
    /**
     * 
     */
    @Value("${spring.datasource.max-active}")
    private int maxActive;
    /**
     * 
     */
    @Value("${spring.datasource.max-wait}")
    private int maxWait;
    /**
     * 
     */
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;
    /**
     * 
     */
    @Value("${spring.datasource.min-evictable-idle-time-millis}")
    private int minEvictableIdleTimeMillis;
    /**
     * 
     */
    @Value("${spring.datasource.test-while-idle}")
    private boolean testWhileIdle;
    /**
     * 
     */
    @Value("${spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;
    /**
     * 
     */
    @Value("${spring.datasource.test-on-return}")
    private boolean testOnReturn;
    /**
     * 
     */
    @Value("${spring.datasource.pool-prepared-statements}")
    private boolean poolPreparedStatements;
    /**
     * 
     */
    @Value("${spring.datasource.max-pool-prepared-statement-per-connection-size}")
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 
     */
    @Value("${spring.datasource.filters}")
    private String filters;
    /**
     * 
     */
    @Value("{spring.datasource.connection-properties}")
    private String connectionProperties;

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年4月10日上午10:23:13
     *@return DataSource
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    public DruidDataSource primaryDataSource() {
        final DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setMinIdle(minIdle);
        datasource.setFailFast(true);
        datasource.setMaxWait(maxWait);
        datasource.setMaxActive(maxActive);
        datasource.setMinIdle(minIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setQueryTimeout(30);
        datasource.setTransactionQueryTimeout(30);
        datasource.setKeepAlive(true);
        datasource.setKillWhenSocketReadTimeout(true);
        datasource.setValidationQueryTimeout(3);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年4月10日上午10:23:00
     *@return JdbcTemplate
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate();
        try {
            jdbcTemplate.setDataSource(primaryDataSource());
            jdbcTemplate.setLazyInit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcTemplate;
    }

    /**]
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年4月9日下午4:52:24
     *@return ServletRegistrationBean
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public ServletRegistrationBean statViewServle() {
        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //IP白名单
        //servletRegistrationBean.addInitParameter("allow","192.168.1.12,127.0.0.1");
        //IP黑名单
        //servletRegistrationBean.addInitParameter("deny","192.168.4.23");
        //控制台用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "6I3YlAlT29NtJX5k");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年4月9日下午4:52:16
     *@return FilterRegistrationBean
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean statFilter() {
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
