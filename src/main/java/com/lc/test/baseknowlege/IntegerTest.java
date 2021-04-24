package com.lc.test.baseknowlege;

import org.junit.jupiter.api.Test;

/**
 * Created by wlc on 2017/8/21 0021.
 */
public class IntegerTest {
    public static void main(String[] args) {
        //下面的语句直接报错：
//        Integer.valueOf(null);
//        testIntegerCache();
        int i = 2 >> 1;
        int k = 2 << 3;
        int j = 2 & 3;
        System.out.println("i-->>"+i+"\nk-->>"+k+"\nj-->>"+j);
    }

    /**
     * short、long、int之间的隐式(+=的写法，编译器会默认强制转换为+左边的类型)和强制转换(=a+b需要手动强制转换为左边的类型)
     */
    @Test
    public void testIntegerToOther(){
        short a = 1;
//		short b = 327672;//超出了short的范围(2byte 16bit，2^16次方=65536)
//		a = a+1; //需要强制转换
        a +=1;
        int c = 20;
        long d = 200;
        c = a+1;
//		c = a + d; //需要强转转换
        String e="3";
        Integer f=2;
//		f+=e; //报错,String无法转换为Integer
        e+=f; //可以正常转换
        char g = 'a'; //单引号表示字符常量
    }


    /**
     * Integer默认情况下存在一个-128到127之间的缓存，在这个区间内的数值，会直接从缓存中取。
     * 1.当使用==进行比较的时候，区间内的值会返回true，区间外的会返回false
     * 2.Integer类型推荐使用equals方法，可以避免这个问题。
     * 3.Integer与int进行比较的时候，会自动进行拆箱，比较数值大小。
     */
    @Test
    public void testIntegerCache(){
        Integer a = 200;
        Integer b = 200;
        int f = 200;
        Integer c = new Integer(20);
        Integer d = 20;
        Integer e = 20;
        //fasle
        System.out.println(a==b);
        //false
        System.out.println(c==d);
        //ture
        System.out.println(d==e);
        //true
        System.out.println(b==f);

        System.out.println(a.equals(b));
        System.out.println(c.equals(d));

        long aLong = 1L;
        aLong+=aLong;
        System.out.printf("长整型数字结果：%d",aLong);

        short s = 0;
        s+=s;
        s=s++;
        //下面的写法报错的，因为2为int类型，int类型与short/long进行计算的时候会自动转换成int，而结果要求的是short类型。
        //        s=s+2;
        //下面的写法就没问题。。。。难道是因为short、int和long的区别吗
        long m = 1L+2;
        System.out.println("s+2"+(s+2));
        System.out.println("\ns:"+s);

        long x = 1;
        x+=x;
        x=x+1;

        short y = 1;
        y+=y;
//        y=y+1;

        Short z = 1;
//        z+=z;
        z++;



    }

    /**
     * 二进制转十进制
     */
    @Test
    public void binaryToDecimalism(){
        //第一个参数为待转换的数据，第2个参数为待转换数据的进制类型
        int result = Integer.parseInt("1000",2);
        System.out.println("result-->>"+result);
    }

    @Test
    public void intAndShortCompare(){
        int a = 1;
        short b = 1;
        Short c = 1;
        Long d = 1L;
        System.out.println("a==b = " + (a == b));
        System.out.println("a==c = " + (a == c));
        System.out.println("c==d = " + (b == d));

    }


}
