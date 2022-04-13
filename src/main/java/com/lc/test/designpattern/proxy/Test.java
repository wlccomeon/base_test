package com.lc.test.designpattern.proxy;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        testCglibProxy();
        testJdkProxy();
//        testJdkProxy0();
    }

    public static void testJdkProxy0(){
        List myList = (List) new JdkDynamicProxy().getProxyObject(new ArrayList<>());
        myList.add("aa");
        myList.add("bb");
        //执行结果：
        //睡眠时间：445
        //jdk动态代理方法参数：["aa"]
        //共用时：501
        //睡眠时间：245
        //jdk动态代理方法参数：["bb"]
        //共用时：246
    }

    public static void testJdkProxy(){
        //实例化一个MathProxy代理对象
        //通过getProxyObject方法获得被代理后的对象
        IMath math=(IMath)new JdkDynamicProxy().getProxyObject(new Math());
        int n1=100,n2=5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }

    public static void testCglibProxy(){
        //实例化一个DynamicProxy代理对象
        //通过getProxyObject方法获得被代理后的对象
        Math math=(Math)new CglibDynamicProxy().getProxyObject(new Math());
        int n1=100,n2=5;
        math.add(n1, n2);
        math.sub(n1, n2);
        math.mut(n1, n2);
        math.div(n1, n2);
    }

}
