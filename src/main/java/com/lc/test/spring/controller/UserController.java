package com.lc.test.spring.controller;

import com.lc.test.designpattern.abstractfactory.AbstractInspection;
import com.lc.test.designpattern.abstractfactory.ConcernInspection;
import com.lc.test.designpattern.abstractfactory.MarkDTO;
import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;
    @Autowired
    private  List<AbstractInspection> inspections;
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

    @GetMapping("/user/inspection")
    public void inspection(){
        MarkDTO markDTO = new MarkDTO();
        markDTO.setDay(0).setCustId("1111");
        for (AbstractInspection inspection : inspections) {
            String mark = inspection.mark(markDTO);
            if (StringUtils.isNotBlank(mark)){
                System.out.println("mark = " + mark);
                //各个子类有优先级，优先级高的有返回值就直接停止
                break;
            }
        }

    }
}
