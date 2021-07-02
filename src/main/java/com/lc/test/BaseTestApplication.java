package com.lc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

/**
 * @description springboot启动类
 * @author wlc
 * @date 2020/11/18 0018 10:27
 */
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class BaseTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseTestApplication.class,args);
    }
}
