package com.lc.test.proxy;

import com.alibaba.fastjson.JSON;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * 使用cglib完成动态代理，大概的原理是：
 * cglib继承被代理的类，重写方法，织入通知，动态生成字节码并运行，因为是继承所以final类是没有办法动态代理的。
 * 实现了一个方法拦截器接口
 */
public class CglibDynamicProxy implements MethodInterceptor {

    /**
     * 被代理对象
     */
    Object targetObject;

    /**
     * Generate a new class if necessary and uses the specified callbacks (if any) to create a new object instance.
     *     Uses the no-arg constructor of the superclass.
     *     动态生成一个新的类，使用父类的无参构造方法创建一个指定了特定回调的代理实例
     * @param object
     * @return
     */
    public Object getProxyObject(Object object) {
        this.targetObject = object;
        //增强器，动态代码生成器
        Enhancer enhancer=new Enhancer();
        //回调方法
        enhancer.setCallback(this);
        //设置生成类的父类类型
        enhancer.setSuperclass(targetObject.getClass());
        //动态生成字节码并返回代理对象
        return enhancer.create();
    }

    /**MethodInterceptor的拦截方法*/
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("cgLib动态代理方法参数："+ JSON.toJSONString(args));
        // 被织入的横切内容，开始时间 before
        long start = System.currentTimeMillis();
        lazy();

        // 调用方法
        Object result = methodProxy.invoke(targetObject, args);

        // 被织入的横切内容，结束时间
        Long span = System.currentTimeMillis() - start;
        System.out.println("共用时：" + span);

        return result;
    }

    /**
     * 模拟延时
     * */
    public void lazy() {
        try {
            int n = (int) new Random().nextInt(500);
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
