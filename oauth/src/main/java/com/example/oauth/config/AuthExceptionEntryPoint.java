package com.example.oauth.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        try {
            System.out.println("进入错误方法");
            response.sendRedirect("http://localhost:9050/oauth/error/401");
        } catch (Exception e) {
            throw new ServletException();
        }
    }

    public boolean isAjaxRequest(HttpServletRequest request) {
        final String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}

