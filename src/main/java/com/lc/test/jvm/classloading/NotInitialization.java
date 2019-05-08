package com.lc.test.jvm.classloading;

/**
 * 非主动使用类演示
 * 下面是3种方式
 */
public class NotInitialization {
    public static void main(String[] args) {
        //通过子类引用父类的静态字段，不会导致子类(subclass)初始化
        System.out.println(SuperClass.value);
        //通过数组定义来引用类，该类（SuperClass)不会被初始化
        SuperClass[] superClasses = new SuperClass[10];
        //常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发ConstantClass类的初始化
        System.out.println(ConstantClass.HELLO_WORLD);

        //如果使用子类，则父类必须初始化（如果子是接口，则在使用的时候父接口才初始化）
        // ??????实际执行时父类并未初始化。。。。
        SubClass subClass = new SubClass();
    }
}
