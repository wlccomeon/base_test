package com.lc.test.designpattern.singleton;

import java.io.Serializable;

/**
 * 饿汉式单例--可是使用静态常量或者静态代码块
 */
public class HungrySingleton implements Serializable {
    private HungrySingleton(){
        if (hungrySingleton!=null){
            throw new RuntimeException("私有构造器禁止反射！");
        }
    }
    //第1种：静态常量
    private final static HungrySingleton hungrySingleton = new HungrySingleton();
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
    //第2中：静态代码块
    private final static HungrySingleton hungrySingleton2;
    static{
        hungrySingleton2 = new HungrySingleton();
    }
    public static HungrySingleton getInstances(){
        return hungrySingleton2;
    }

    //添加该方法可以防止反序列化造成的单例失效问题
    private Object readResolve(){
        return hungrySingleton;
    }
}
