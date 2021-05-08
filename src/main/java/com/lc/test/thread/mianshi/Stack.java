package com.lc.test.thread.mianshi;

import java.util.LinkedList;

public class Stack {
    LinkedList list = new LinkedList();
    public synchronized void push(Object x) {
        System.out.println("77777 = " + 77777);
        synchronized(list) {
            System.out.println("2222 = " + 22222);
            list.addLast( x );
            System.out.println("3333 = " + 3333);
            notify();
        }
    }

    public synchronized Object pop() throws Exception {
        synchronized(list) {
            if( list.size() <= 0 ) {
                System.out.println("44444 = " + 44444);
                wait();
                System.out.println("55555 = " + 55555);
            }
            System.out.println("66666 = " + 66666);
            return list.removeLast();
        }
    }
}
