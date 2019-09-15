package com.lz.cloud.demo.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-24 15:53
 */
@RestController
@Slf4j
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity demo(){
        log.info("访问到demo1项目中");
        return ResponseEntity.ok().body("成功");
    }

}
