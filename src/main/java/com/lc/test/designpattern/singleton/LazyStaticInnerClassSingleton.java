package com.lc.test.designpattern.singleton;

/**
 * 懒汉式中的静态内部类单例---解决指令重排对多个线程可见造成的问题
 * @author wlc
 */
public class LazyStaticInnerClassSingleton {
    private LazyStaticInnerClassSingleton(){}
    private static class InnerClass{
        private static LazyStaticInnerClassSingleton singleton = new LazyStaticInnerClassSingleton();
    }
    public static LazyStaticInnerClassSingleton getInstance(){
        return InnerClass.singleton;
    }
}
