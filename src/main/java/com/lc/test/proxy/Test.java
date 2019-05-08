package com.lc.test.proxy;

public class Test {

    public static void main(String[] args) {
        testCglibProxy();
        testJdkProxy();
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
