package com.lc.test.thread.mianshi;

import java.util.LinkedList;

public class Stack {
    LinkedList list = new LinkedList();

    public synchronized void push(Object x) {
        list.addLast(x);
        this.notify();
    }

    public synchronized Object pop() throws Exception {
        if (list.size() <= 0) {
            this.wait();
        }
        return list.removeLast();
    }
}
