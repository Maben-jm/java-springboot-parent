package com.jsp.controller;

import com.alibaba.fastjson.JSONObject;
import com.maben.util.MysqlUtils;
import com.maben.util.SYSTEM_TYPE;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/22
 */
@RestController
@RequestMapping("progressbar")
public class ProgressBarController {
    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("progressbar/progressbar");
        return model;
    }
    @RequestMapping("progressbar-my")
    public ModelAndView progressbar_my() {
        ModelAndView model = new ModelAndView("progressbar/progressbar-my");
        return model;
    }

    @RequestMapping(value = "importTest", method = RequestMethod.POST)
    public void importTest(HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        final boolean recover = MysqlUtils.recover2("dump.sql", "localhost", "test", "root", "root", SYSTEM_TYPE.WINDOWS, response);
        final JSONObject result = new JSONObject();
        if (recover) {
            result.put("code", 0);
        } else {
            result.put("code", 1);
            result.put("msg", "恢复数据库失败");
        }
        final PrintWriter writer = response.getWriter();
        writer.write(result.toJSONString());
        writer.flush();
        writer.close();
    }
    @RequestMapping(value = "exportTest", method = RequestMethod.POST)
    public void exportTest(HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        final boolean backup =MysqlUtils.backup("localhost","root","root",".","dump","djxt");
        final Map<String,Object> result = new HashMap<>();
        if (backup) {
            result.put("code", 0);
        } else {
            result.put("code", 1);
            result.put("msg", "恢复数据库失败");
        }
        final PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(result));
        writer.flush();
        writer.close();
    }
}
