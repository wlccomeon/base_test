package com.lc.test.spring.entity;

public class Student {

    public void eat(String name) {
        System.out.println(name+"正在吃饭...");
    }

    /**
     * 该方法不能被子类代理
     * @param name
     */
    private void sleep(String name){
        System.out.println(name+"正在偷偷睡觉...");
    }
}
