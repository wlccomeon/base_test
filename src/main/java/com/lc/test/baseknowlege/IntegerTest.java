package com.lc.test.baseknowlege;

import com.lc.test.spring.controller.FileController;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

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
        //a==b = true
        //a==c = true
        //c==d = true
    }

    @Test
    public void swapInt(){
        Integer a = 1;
        Integer b = 2;
//        System.out.println("before swap:a="+a+",b="+b);
//        swap1(a,b);
//        System.out.println("after swap:a="+a+",b="+b);
        //结果：
        //before swap:a=1,b=2
        //swap1方法内：a=2,b=1
        //after swap:a=1,b=2

//        System.out.println("before swap:a="+a+",b="+b);
//        swap2(a,b);
//        System.out.println("after swap:a="+a+",b="+b);
        //结果：
        //before swap:a=1,b=2
        //swap2方法内：a=2,b=2
        //after swap:a=2,b=2

        System.out.println("before swap:a="+a+",b="+b);
        swap3(a,b);
        System.out.println("after swap:a="+a+",b="+b);
        //结果：
        //before swap:a=1,b=2
        //swap2方法内：a=2,b=2
        //after swap:a=2,b=2
    }

    /**
     * 交换两个数字的值,Integer是8大基本数据类型，内部的value为final类型，
     * 为值传递，非引用传递，只能在该方法内部进行数字交换，不能影响到调用方
     * @param num1
     * @param num2
     */
    private void swap1(Integer num1,Integer num2){
        Integer temp = null;
        temp = num1;
        num1 = num2;
        num2 = temp;
        System.out.println("swap1方法内：a="+num1+",b="+num2);
    }

    /**
     * 交换两个数字的值
     * 这个方法中使用了反射来操作Integer的值，不过需要注意的是，如果num1和num2处于-128-127之间的话，交换的值可能会出问题
     * @param num1
     * @param num2
     */
    private void swap2(Integer num1,Integer num2) {
        try {
            //获取Integer中的value字段,并允许重新赋值
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            //num1指针指向的位置为IntegerCache.cache[i + (-IntegerCache.low)]即[1+128]=[129]的位置为1
            int temp = num1.intValue();
            //将num1也就是第[129]位置上的值变为num2指针所指向的值2。
            field.set(num1,num2);
            //此时temp1指针所指向的位置跟num1指针指向的位置是一样的，已经变为了2，所以，这里给num2指针指向的位置[2+128]=[130]赋值之后num2变成了2.
            field.set(num2,temp);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("swap2方法内：a="+num1+",b="+num2);
    }

    /**
     * 交换两个数字的值
     * 这个方法中使用了反射来操作Integer的值，不过需要注意的是，如果num1和num2处于-128-127之间的话，交换的值可能会出问题
     * 解决方法：1.使用new Integer方式，2.使用setInt方法
     * @param num1
     * @param num2
     */
    private void swap3(Integer num1,Integer num2) {
        try {
            //获取Integer中的value字段,并允许重新赋值
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            //num1指针指向的位置为IntegerCache.cache[i + (-IntegerCache.low)]即[1+128]=[129]的位置为1
            int temp = num1.intValue();
            //将num1也就是第[129]位置上的值变为num2指针所指向的值2。
            field.set(num1,num2);
            //此时temp1指针所指向的位置跟num1指针指向的位置是一样的，已经变为了2，所以，这里给num2指针指向的位置[2+128]=[130]赋值之后num2变成了2.
//            field.set(num2,temp);
            //解决方法1：使用new的方式，就不会使用Integer的缓存了
//            field.set(num2,new Integer(temp));
            //解决方法2：或者使用setInt方法，不会产生装箱拆箱
            field.setInt(num2,temp);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("swap3方法内：a="+num1+",b="+num2);

    }

}
