package com.lc.test.designpattern.flyweight;

import com.lc.test.designpattern.observer.WechatServer;
import com.lc.test.entity.User;

/**
 * @author wlc
 * @desc
 * @date 2020-10-19 18:26:27
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("args = " + args);
        System.out.println("getUser(WechatServer.class) = " + getUser(WechatServer.class));
    }

    public static <T> T getUser(Class<T> clazz){
        if (clazz.isInstance(User.class)){
            return (T) new User();
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
