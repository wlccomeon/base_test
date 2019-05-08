package com.lc.test.jvm.classloading;

public class SubClass extends SuperClass{
    static {
        System.out.println("subclass init!");
    }
}
