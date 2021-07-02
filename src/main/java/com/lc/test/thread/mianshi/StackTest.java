package com.lc.test.thread.mianshi;

import java.util.LinkedList;

/**
 * 下面的代码在什么情况下会出现问题？根源是什么？
 */
public class StackTest {

    public static void main(String[] args) throws InterruptedException {

        Stack myStack = new Stack();
        Thread t1 = new Thread(() -> {
            try {
                myStack.pop();
                System.out.println("99999 = " + 99999);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        t1.start();
        //确保t1线程首先执行
        Thread.sleep(1000);
        myStack.push(1);
        System.out.println("myStack.list = " + myStack.list);
        myStack.push(2);
        System.out.println("myStack = " + myStack.list);
    }

}
