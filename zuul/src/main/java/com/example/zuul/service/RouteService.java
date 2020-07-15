package com.example.zuul.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

public interface RouteService {
    List<RouteDefinition> getRoutes()throws Exception;
}
