package com.lc.test.designpattern.singleton;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        System.out.println("main thread"+ThreadLocalInstance.getInstance());
//        Thread t1 = new Thread(new T());
//        Thread t2 = new Thread(new T());
//        t1.start();
//        t2.start();
//        System.out.println("运行结束。");

        //序列化和反序列化破坏单例
//        HungrySingleton instance = HungrySingleton.getInstance();
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton_file"));
//        oos.writeObject(instance);
//
//        File file = new File("singleton_file");
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//        HungrySingleton serilizeInstance = (HungrySingleton)ois.readObject();
//        System.out.println(instance == serilizeInstance);
        //false，问题出在ois.readObject()方法中
        //原因：在该方法调用的方法java.io.ObjectInputStream.readOrdinaryObject中，返回值之前会调用java.io.ObjectStreamClass.isInstantiable方法进行判断
        //是否通过反射产生一个新的对象，其判断依据就是实例是否实现了序列化。在这里HungrySingleTon肯定实现了序列化接口，导致产生新的实例。如果我们不做处理，
        //就会返回新产生的实例。
        //解决方法：在java.io.ObjectInputStream.readOrdinaryObject中，当产生新的实例之后，会在下面有个判断，原来的实例中是否有readResolve()这个方法，
        //如果有，则执行。那么解决方法就是：在HungrySingleton类中添加这个readResolve()方法，并在这个方法中返回原来的实例。
        //也就是说：反射还是会生成一个新的实例，但不会被用上。我们最终拿到的还是原来的实例。

//        Class classObject =HungrySingleton.class;
//        //反射也会破坏单例
//        Constructor constructor = classObject.getDeclaredConstructor();
//        //私有构造器的权限给放开
//        constructor.setAccessible(true);
//        HungrySingleton newHungryInstance = (HungrySingleton)constructor.newInstance();
//        System.out.println(instance==newHungryInstance);
        //上述结果就是false
        //原因：通过反射，将私有构造器的权限给公开了，从而在newInstance的时候直接产生了新的实例
        //解决方法：在私有构造器当中加一个判断实例是否为空的逻辑:
//        if (hungrySingleton!=null){
//            throw new RuntimeException("私有构造器禁止反射！");
//        }
        //上述的解决方法也适用于饿汉式类型的单例

        //而对于懒汉式中的单例则不一定生效。
        Class clazz = LazySingleton.class;
        Constructor lazyConstructor = clazz.getDeclaredConstructor();
        lazyConstructor.setAccessible(true);
        LazySingleton reflectLazySingleton = (LazySingleton)lazyConstructor.newInstance();
        LazySingleton lazySingleton = LazySingleton.getInstance();
        System.out.println(lazySingleton==reflectLazySingleton);
        //上述结果返回为false，即使在其中加上上面例子中的判断也不行。如果定义一个flag，对实例的创建情况进行控制
        //是否也可以呢？答案是不可以。因为反射也可以拿到私有的成员变量。

        //接下来就是个大杀器，既能防止反射也能防止序列化和反序列化对单例模式的破坏:枚举单例模式,这也是
        //Effective Java 这本书中所推荐的，适用按本是jdk1.5以后。

        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
    }
}
