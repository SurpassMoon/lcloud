package com.lz.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-22 20:28
 */

@SpringBootApplication
//@EnableDiscoveryClient
////@EnableFeignClients(basePackages = "com.springboot.cloud.auth.client")
//@EnableCircuitBreaker
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
