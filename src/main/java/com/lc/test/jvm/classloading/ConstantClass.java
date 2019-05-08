package com.lc.test.jvm.classloading;

public class ConstantClass {
    static{
        System.out.println("ConstantClass init!");
    }
    public static String HELLO_WORLD="hello world!";
}
