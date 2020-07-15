package com.example.zuul.mapper;

import com.maben.pojo.RouteModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
public interface RouteMapper {

    @Select("select * from t_route_config where deleted = 0 ")
    List<RouteModel> getRouteModel()throws Exception;

}
