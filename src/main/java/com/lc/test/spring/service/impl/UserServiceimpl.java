package com.lc.test.spring.service.impl;

import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Override
    public List<User> list() {
        User user = new User();
        user.setId(1);
        user.setName("wlc");
        user.setAddress("北京");
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }
}
