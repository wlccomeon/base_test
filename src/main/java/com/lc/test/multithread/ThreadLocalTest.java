package com.lc.test.multithread;

import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    /**第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次(实际测试并没有调用)*/
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            System.out.println("thread local initial time:"+System.currentTimeMillis());
            return System.currentTimeMillis();
        }
    };

    public static void main(String[] args) {
        ThreadLocalTest.begin();
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Cost:"+ThreadLocalTest.end()+"mills");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final Long end(){
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }
}
