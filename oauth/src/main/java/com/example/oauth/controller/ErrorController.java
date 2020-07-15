package com.example.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public String error_401(){
        return "err/error_401";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error_404(){
        return "err/error_404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String error_500(){
        return "err/error_500";
    }

}

