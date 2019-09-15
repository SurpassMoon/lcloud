package com.lz.cloud.gateway.route;

import com.lz.cloud.gateway.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-24 12:13
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private RouteService routeService;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return routeService.getRouteDefinitions();
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return routeService.save(route);
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeService.delete(routeId);
    }
}
