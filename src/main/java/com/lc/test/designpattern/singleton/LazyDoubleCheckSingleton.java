package com.lc.test.designpattern.singleton;

/**
 * 双重检查：在双重检查的第二个检查中实例化对象期间可能会发生编译重排，导致出现多个实例。
 *           解决方法之一：禁止重排，将成员变量使用volatile修饰
 *           解决方法之二：重排对多线程不可见，使用静态内部类实例化对象
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton lazySingleton=null;
    private LazyDoubleCheckSingleton(){}
    public static LazyDoubleCheckSingleton getInstance(){
        if (lazySingleton==null){
            synchronized (LazyDoubleCheckSingleton.class){
                if (lazySingleton==null){
                    lazySingleton = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazySingleton;
    }
}
