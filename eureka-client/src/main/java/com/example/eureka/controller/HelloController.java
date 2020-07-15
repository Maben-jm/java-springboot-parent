package com.example.eureka.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/7
 */
@RestController
public class HelloController {
    @RequestMapping("hello")
    public String hello(){
        return "hello eureka-client";
    }
}
