package com.lc.test.designpattern.proxy;

public class Math implements IMath{
    //加
    @Override
    public int add(int n1, int n2){
        int result=n1+n2;
        System.out.println(n1+"+"+n2+"="+result);
        return result;
    }


    //减
    @Override
    public int sub(int n1, int n2){
        int result=n1-n2;
        System.out.println(n1+"-"+n2+"="+result);
        return result;
    }

    //乘
    @Override
    public int mut(int n1, int n2){
        int result=n1*n2;
        System.out.println(n1+"X"+n2+"="+result);
        return result;
    }

    //除
    @Override
    public int div(int n1, int n2){
        int result=n1/n2;
        System.out.println(n1+"/"+n2+"="+result);
        return result;
    }
}
