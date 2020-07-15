package com.example.zuul.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zuul.mapper.RouteMapper;
import com.example.zuul.service.RouteService;
import com.maben.pojo.RouteModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<RouteDefinition> getRoutes() throws Exception {
        final List<RouteModel> routeModels = routeMapper.getRouteModel();
        List<RouteDefinition> result = new ArrayList<>();
        for (RouteModel routeModel : routeModels) {
            if (routeModel.getEnabled() != null && routeModel.getEnabled() == 1) {
                RouteDefinition routeDefinition = new RouteDefinition();
                routeDefinition.setId(routeModel.getRouteId());
                routeDefinition.setUri(new URI(routeModel.getRouteUri()));
                final Integer routeOrder = routeModel.getRouteOrder();
                if (routeOrder != null) {
                    routeDefinition.setOrder(routeOrder);
                }
                final String filtersStr = routeModel.getFilters();
                if (StringUtils.isNotBlank(filtersStr)) {
                    final JSONArray array = JSONArray.parseArray(filtersStr);
                    List<FilterDefinition> filterDefinitions = new ArrayList<>();
                    for (Object obj : array) {
                        JSONObject json = (JSONObject) obj;
                        FilterDefinition filterDefinition = new FilterDefinition();
                        filterDefinition.setName(json.getString("name"));
                        filterDefinition.setArgs(jsonToMap(json.getJSONObject("args")));
                        filterDefinitions.add(filterDefinition);
                    }
                    routeDefinition.setFilters(filterDefinitions);
                }
                final String predicatesStr = routeModel.getPredicates();
                if (StringUtils.isNotBlank(predicatesStr)) {
                    final JSONArray array = JSONArray.parseArray(predicatesStr);
                    List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
                    for (Object obj : array) {
                        JSONObject json = (JSONObject) obj;
                        PredicateDefinition predicateDefinition = new PredicateDefinition();
                        predicateDefinition.setName(json.getString("name"));
                        predicateDefinition.setArgs(jsonToMap(json.getJSONObject("args")));
                        predicateDefinitions.add(predicateDefinition);
                    }
                    routeDefinition.setPredicates(predicateDefinitions);
                }
                result.add(routeDefinition);
            }
        }
        return result;
    }

    private Map<String, String> jsonToMap(JSONObject args) {
        Map<String, String> result = new HashMap<>();
        for (String key : args.keySet()) {
            result.put(key, args.getString(key));
        }
        return result;
    }
}
