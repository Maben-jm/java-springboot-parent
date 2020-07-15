package com.jsp.service.impl;

import com.jsp.service.RouteService;
import com.maben.pojo.RouteModel;
import com.maben.query.RouteModelQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.jsp.mapper.RouteMapper;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    RouteMapper routeMapper;

    @Override
    public Map<String, Object> getRoutes(RouteModelQuery routeModelQuery) throws Exception {
        Map<String, Object> result = new HashMap<>();
        final List<RouteModel> list = routeMapper.getRouteModel(routeModelQuery);
        final Integer count = routeMapper.getRouteModelCount(routeModelQuery);
        result.put("list", list);
        result.put("count", count);
        return result;
    }

    @Override
    public RouteModel getRouteById(Integer id) throws Exception {
        if (id == null) {
            return null;
        }
        return routeMapper.getRouteById(id);
    }

    @Override
    public void save(RouteModelQuery routeModelQuery) throws Exception {
        if (Objects.isNull(routeModelQuery.getRouteOrder())){
            routeModelQuery.setRouteOrder(0);
        }
        if (routeModelQuery.getId() != null) {
            routeModelQuery.setUpdateTime(new Date());
            routeMapper.update(routeModelQuery);
        } else {
            routeModelQuery.setDeleted(0);
            routeModelQuery.setCreateTime(new Date());
            routeMapper.insert(routeModelQuery);
        }
    }
}
