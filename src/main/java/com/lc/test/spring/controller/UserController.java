package com.lc.test.spring.controller;

import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @Transactional
    @RequestMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @RequestMapping("/list/private")
    private List<User> privateList() {
        //该方法中获取到的userService为null
        return userService.list();
    }

    @PostMapping("/user/single")
    public User getUser(@RequestBody User user,@RequestParam(value = "uid",required = false) Integer uid){
        System.out.println("uid = " + uid);
        System.out.println("user.toString() = " + user.toString());
        return null;
    }
}
