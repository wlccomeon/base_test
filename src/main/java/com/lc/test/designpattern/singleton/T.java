package com.lc.test.designpattern.singleton;

public class T implements Runnable {
    @Override
    public void run() {
        LazySingleton singleton = LazySingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+"获取的实例为："+singleton);

        ContainerSingleton.putInstance("object",new Object());
        Object instance = ContainerSingleton.getInstance("object");
//
//        ThreadLocalInstance instance = ThreadLocalInstance.getInstance();
//        System.out.println(Thread.currentThread().getName()+"  "+instance);
    }
}
