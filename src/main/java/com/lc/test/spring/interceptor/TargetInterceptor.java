package com.lc.test.spring.interceptor;

import com.lc.test.spring.entity.Student;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TargetInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method,
                            Object[] params, MethodProxy proxy) throws Throwable {
        System.out.println("调用前");
        Object result = proxy.invokeSuper(obj, params);
        System.out.println("调用后");
        return result;
    }

    public static void main(String[] args) throws Exception {

        //创建字节码增强器
        Enhancer enhancer =new Enhancer();
        //设置父类
        enhancer.setSuperclass(Student.class);
        //设置回调函数
        enhancer.setCallback(new TargetInterceptor());
        //创建代理类(子类拥有父类非 private 的属性、方法。)
        Student student=(Student)enhancer.create();
        student.eat("王二杆子");

        //通过反射，强行调用父类的private方法
        //会报Exception in thread "main" java.java.lang.NoSuchMethodException:
        // com.lc.test.spring.entity.Student$$EnhancerByCGLIB$$64aa0a59.sleep(java.java.lang.String)
        Method sleep = student.getClass().getMethod("sleep", String.class);
        sleep.setAccessible(true);
        sleep.invoke(student,"王二杆子");
    }
}