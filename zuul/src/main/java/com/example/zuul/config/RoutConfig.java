package com.example.zuul.config;

import com.example.zuul.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/9
 */
@Component
public class RoutConfig implements RouteDefinitionRepository {

    @Autowired
    RouteService routeService;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routes = new ArrayList<>();
        try {
            routes = routeService.getRoutes();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Flux.fromIterable(routes);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
