package com.lc.test.spring.controller;

import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @RequestMapping("/list/private")
    private List<User> privateList() {
        //该方法中获取到的userService为null
        return userService.list();
    }
}
