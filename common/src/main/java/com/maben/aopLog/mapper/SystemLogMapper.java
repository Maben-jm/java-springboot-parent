package com.maben.aopLog.mapper;


import com.maben.aopLog.po.SystemLog;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface SystemLogMapper {
    @Delete("delete from t_log where id=#{id}")
    int deleteByPrimaryKey(String id);

    @Insert("insert into t_log (description,method,log_type,request_ip,exception_code,exception_detail,params,user,create_time) values " +
                            "( #{description},#{method},#{logType},#{requestIp},#{exceptionCode},#{exceptionDetail},#{params},#{user},#{createTime} )")
    int insert(SystemLog record);

    @Select("select * from t_log where id =#{id}")
    SystemLog selectByPrimaryKey(String id);
}