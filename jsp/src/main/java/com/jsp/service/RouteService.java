package com.jsp.service;


import com.maben.pojo.RouteModel;
import com.maben.query.RouteModelQuery;

import java.util.Map;

public interface RouteService {

    Map<String,Object> getRoutes(RouteModelQuery routeModelQuery)throws Exception;

    RouteModel getRouteById(Integer id)throws Exception;

    void save(RouteModelQuery routeModelQuery)throws Exception;
}
