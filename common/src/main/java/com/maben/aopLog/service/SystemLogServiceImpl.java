package com.maben.aopLog.service;

import com.maben.aopLog.po.SystemLog;
import com.maben.aopLog.mapper.SystemLogMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {
    @Resource
    private SystemLogMapper systemLogMapper;
    @Override
    public int deleteSystemLog(String id) {
        return systemLogMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int insert(SystemLog record) {
        return systemLogMapper.insert(record);
    }
    @Override
    public SystemLog selectSystemLog(String id) {
        return systemLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertTest(SystemLog record) {
        return systemLogMapper.insert(record);
    }

}