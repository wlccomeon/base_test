package com.lc.test.baseknowlege;

/**
 * Created by wlc on 2017/8/21 0021.
 */
public class IntegerTest {
    public static void main(String[] args) {
        //下面的语句直接报错：
//        Integer.valueOf(null);
        testIntegerCache();
    }

    /**
     * Integer默认情况下存在一个-128到127之间的缓存，在这个区间内的数值，会直接从缓存中取。
     * 1.当使用==进行比较的时候，区间内的值会返回true，区间外的会返回false
     * 2.Integer类型推荐使用equals方法，可以避免这个问题。
     */
    public static void testIntegerCache(){
        Integer a = 200;
        Integer b = 200;
        //fasle
        System.out.println(a==b);
        Integer c = 20;
        Integer d = 20;
        //true
        System.out.println(c==d);

        System.out.println(a.equals(b));
        System.out.println(c.equals(d));
    }
}
