package com.jsp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsp.service.UserService;
import com.maben.aopLog.Log;
import com.maben.pojo.UserModel;
import com.maben.query.UserModelQuery;
import com.maben.util.HttpClientUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/11
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "")
    public String toLogin() {
        return "login";
    }
    @GetMapping(value = "index")
    public String toIndex() {
        return "index";
    }

    @RequestMapping(value = "handleLogin", method = RequestMethod.POST)
    @ResponseBody
    @Log(operationType="login操作",operationName="登录")
    public Map<String, Object> handleLogin(UserModelQuery userModelQuery, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        try {
            final String userName = userModelQuery.getUserName();
            if (StringUtils.isBlank(userName)) {
                result.put("success", "0");
                result.put("msg", "用户名为空");
                return result;
            }
            final String password = userModelQuery.getPassword();
            if (StringUtils.isBlank(password)) {
                result.put("success", "0");
                result.put("msg", "密码为空");
                return result;
            }
            final UserModel userModel = userService.getUserModelByUserName(userName);
            if (userModel == null) {
                result.put("success", "0");
                result.put("msg", "无效的用户");
                return result;
            }
            if (!new BCryptPasswordEncoder(10).matches(password, userModel.getPassword())) {
                result.put("success", "0");
                result.put("msg", "密码不正确");
                return result;
            }
            Map<String,String> map = new HashMap<>();
            map.put("client_id","system_pass");
            map.put("grant_type","password");
            map.put("client_secret","123321");
            map.put("username",userName);
            map.put("password",password);
            final String tokenStr = HttpClientUtil.post("http://localhost:9050/oauth/oauth/token", map);
            final JSONObject object = JSONObject.parseObject(tokenStr);
            final String access_token = object.getString("access_token");
            result.put("success","1");
            result.put("access_token",access_token);
            //将用户信息存入session
            saveToSession(access_token,request,userModel);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "0");
            result.put("msg", "登录失败," + e.getMessage());
        }
        return result;
    }

    private void saveToSession(String access_token, HttpServletRequest request, UserModel userModel) {
        final Map<String, Object> sessionMap = new HashMap<String, Object>();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        sessionMap.put("ipdz", ip);
        sessionMap.put("userName", userModel.getUserName());
        redisTemplate.opsForValue().set("String:SESSION:" + access_token, JSONObject.toJSONString(sessionMap));
    }

}
