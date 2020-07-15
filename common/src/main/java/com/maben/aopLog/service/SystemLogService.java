package com.maben.aopLog.service;


import com.maben.aopLog.po.SystemLog;

public interface SystemLogService {

    int deleteSystemLog(String id);

    int insert(SystemLog record);

    int insertTest(SystemLog record);

    SystemLog selectSystemLog(String id);

}