package com.lc.test.multithread;

public class HappensBeforeTest {

    private static String myStr;

    public static void main(String[] args) {

//        myStr = "b";
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myStr = "c";
                System.out.println("t1:myStr-->>"+myStr);
            }
        },"tt1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myStr = "d";
                System.out.println("t2:myStr-->>"+myStr);
            }
        });
        t2.start();
        t1.start();
        System.out.println("main:myStr-->>"+myStr);


    }

}
