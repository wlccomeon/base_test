package com.lc.test.spring.autowired;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author wlc
 * @desc: AutowiredBeansTest
 * @datetime: 2024/6/27 0027 17:56
 */
@Slf4j
@Controller
@RequestMapping(value = "/autowiredTest")
public class AutowiredBeansTest {


    @Autowired
    List<UserService> userServiceList;

    @GetMapping("/list")
    public void privateList() {
        //该方法中获取到的userService为null
        for (UserService userService : userServiceList) {
            System.out.println("userService.list() = " + JSON.toJSONString(userService.list()));
        }
    }

}

