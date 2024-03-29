package com.lz.cloud.gateway.filter;

import com.lz.cloud.auth.client.service.AuthServcie;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-24 22:34
 */
@Configuration
@ComponentScan(basePackages = "com.lz.cloud.auth.client")
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {

    private final static String X_CLIENT_TOKEN_USER = "x-client-token-user";
    private final static String X_CLIENT_TOKEN = "x-client-token";

    public static final String BEARER = "Bearer";

    @Autowired
    private AuthServcie authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        log.info("url:{},method:{},headers:{}", url, method, request.getHeaders());
        //不需要网关签权的url
        if (authService.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }

        if (StringUtils.isBlank(authentication) || !authentication.startsWith(BEARER)) {
            log.debug("url:{},method:{},headers:{}, 请求未携带token信息", url, method, request.getHeaders());
            return unauthorized(exchange);
        }

        if (authService.hasPermission(authentication, url, method)) {
            ServerHttpRequest.Builder builder = request.mutate();
            //TODO 转发的请求都加上服务间认证token
            builder.header(X_CLIENT_TOKEN, "TODO zhoutaoo添加服务间简单认证");
            //将jwt token中的用户信息传给服务
            builder.header(X_CLIENT_TOKEN_USER, authService.getJwt(authentication).getClaims());
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }

//        return chain.filter(exchange.mutate().request(builder.build()).build());
        return chain.filter(exchange);
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }
}
