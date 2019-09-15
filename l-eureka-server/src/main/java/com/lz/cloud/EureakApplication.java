package com.lz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-22 19:59
 */

@SpringBootApplication
@EnableEurekaServer
public class EureakApplication {

    public static void main(String[] args) {
        SpringApplication.run(EureakApplication.class, args);
    }

}
