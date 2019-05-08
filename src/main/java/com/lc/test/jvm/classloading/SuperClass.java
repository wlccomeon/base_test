package com.lc.test.jvm.classloading;

public class SuperClass {
    static {
        System.out.println("superclass init!");
    }
    public static int value=123;
}
