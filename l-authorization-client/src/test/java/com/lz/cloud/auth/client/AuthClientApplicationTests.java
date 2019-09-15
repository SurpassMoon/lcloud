package com.lz.cloud.auth.client;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 李喆
 * @Description:
 * @Date: 2019/9/14 8:01 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthClientApplicationTests {

    @Value("${gate.ignore.authentication.startWith}")
    private String ignoreUrls = "/oauth";

    @Test
    public void test(){
        System.out.println(ignoreUrls);
    }

}
