package com.lc.test.designpattern.singleton;

/**
 * 枚举单例：在jdk源码中，枚举根本没有私有构造器，而且会有个判断：如果是枚举类，则不创建新的实例
 *          从而可以防止序列化、反序列化对单例的破坏；同时，对反射也不支持
 *          原理：通过反编译可以查看到枚举单例，其实是通过静态代码块形成的饿汉式单例，加上jdk对
 *          枚举的序列化和反射都进行了特殊的处理，从而保证单例的唯一性。
 *
 *          枚举中其实也支持方法，例如在下例中，需要在INSTANCE中写一个protected方法，在INSTANCE外面也需要
 *          写一个抽象的同名方法，否则外部是无法调到INSTANCE中的方法的。可以通过jad反编译工具查看原理。
 *
 */
public enum EnumSingleton {
    INSTANCE{
        @Override
        protected void testPrint(){
            System.out.println("this is lc print!");
        }
    };

    protected abstract void testPrint();
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
