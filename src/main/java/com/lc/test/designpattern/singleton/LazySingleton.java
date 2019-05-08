package com.lc.test.designpattern.singleton;

/**
 * 懒汉式单例：
 *    延迟加载，使用时再初始化
 */
public class LazySingleton {
    private static LazySingleton lazySingleton=null;
    private LazySingleton(){
        if (lazySingleton!=null){
            throw new RuntimeException("私有构造器禁止反射！");
        }
    }
    public synchronized static LazySingleton getInstance(){
        if (lazySingleton==null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
