package com.jsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class HelloController {

    @RequestMapping("info")
    @ResponseBody
    public String sayHello() {
        return "hello jsp application";
    }
    
    @GetMapping(value = "")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello jsp";
    }



    @Autowired
    RestTemplate restTemplate;
    @RequestMapping("token")
    @ResponseBody
    public String getToken(String userName,String userPwd){
        String token= restTemplate.getForObject("http://EURAKA-CLIENT/hello",String.class);
        System.out.println("----------测试是否可以调用成功 hello:"+token);
//        token= restTemplate.getForObject("http://OAUTH/oauth/token?client_id=system&grant_type=password&client_secret=system&username=" + userName + "&password=" + userPwd,String.class);
        return token;
    }
    
}
