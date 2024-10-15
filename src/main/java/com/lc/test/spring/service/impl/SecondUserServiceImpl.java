package com.lc.test.spring.service.impl;

import com.lc.test.entity.User;
import com.lc.test.spring.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wlc
 * @desc: SecondUserServiceImpl
 * @datetime: 2024/6/27 0027 17:57
 */
@Service
@Qualifier("secondUserServiceImpl")
public class SecondUserServiceImpl implements UserService {

    @Override
    public List<User> list() {
        User user = new User();
        user.setId(1);
        user.setName("wlc啊啊");
        user.setAddress("帝都");
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }
}

