package com.lc.test.jvm;

public class TestClass{

    private int m;

    public int inc(){
        int n = m+1;
        System.out.println("哈哈哈哈"+n);
        return n;
    }

    public String hellowWorld(){
        String helloWolrd = "helloWolrd!";
        System.out.println(helloWolrd);
        return helloWolrd;
    }

    public int inc1(){
        int x;
        try{
            x=1;
//            return x;
            throw new NumberFormatException("1111");
        }catch(Exception e){
            x=2;
            return x;
        }finally{
            x=3;
            System.out.println("x---"+x);
        }
    }

    public static void main(String[] args){
        TestClass testClass = new TestClass();
        testClass.inc();
        testClass.hellowWorld();
        System.out.println(testClass.inc1());
    }
}
