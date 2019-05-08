package com.lc.test.multithread;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Demino(previous),String.valueOf(i));
            thread.start();
            previous=thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+" terminate.");

//        Thread t1 = new Thread(new T(),"t1");
//        Thread t2 = new Thread(new T(),"t2");
//        Thread t3 = new Thread(new T(),"t3");
//        t1.start();
//        t2.start();
//        t3.start();
//        //下面的写法没有用，只是将3个子线程都join到了main线程上，他们之间的执行顺序还是乱的。并不能保证有序，使用debug方式可看出来。
//        t2.join();
//        t1.join();
//        t3.join();
    }
    static class Demino implements Runnable{
        private Thread thread;
        public Demino(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                thread.join();
            }catch (Exception e){

            }

            System.out.println(Thread.currentThread().getName()+" demino innerclass thread terminate.");
        }
    }

    static class T implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" running...");
        }
    }
}
