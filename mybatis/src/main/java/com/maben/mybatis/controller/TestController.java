package com.maben.mybatis.controller;

import com.maben.mybatis.mapper.DyxxMapper;
import com.maben.util.MysqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DyxxMapper dyxxMapper;

    @RequestMapping("dyxx/{id}")
    public Object getDyxxById(@PathVariable("id") String id)throws Exception{
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return dyxxMapper.getDyxxById(id);
    }

    @Value("${param.mysql-ip}")
    private String MYSQL_IP;

    @RequestMapping("dump-test")
    public String dumpMysql(){
        MysqlUtils.backup(MYSQL_IP,"root","root",".","dump","djxt");
        return "success";
    }

    @RequestMapping("import-test")
    public String importMysql(){
        MysqlUtils.recover("dump.sql",MYSQL_IP,"test","root","root");
        return "success";
    }

}
