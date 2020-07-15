package com.example.oauth.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要放开权限的url
 *
 */
public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] endPoints = { "/health", "/env", "/metrics", "/trace", "/dump", "/jolokia", "/info", "/logfile", "/refresh", "/flyway", "/liquibase", "/heapdump", "/loggers",
        "/auditevents", "/v2/api-docs/**","/actuator/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**","/druid/**" };

    /**
     * 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return endPoints;
        }

        final List<String> list = new ArrayList<String>();
        for (String url : endPoints) {
            list.add(url);
        }
        for (String url : urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

}
