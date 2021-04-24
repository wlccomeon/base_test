package com.lc.test.algorithm;

import org.junit.Test;

import java.util.Random;
import java.util.Scanner;

/**
 * @author wlc
 * @description 随机生成指定范围内的正负值
 * @date 2021/4/7 0007 20:18
 */
public class RandomnNum {

    @Test
    public void testDoubleRandom(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            double v = random.nextDouble();
            System.out.println("v = " + v);
        }
        //v = 0.8856455335713949
        //v = 0.208993782747676
        //v = 0.10261398195660931
        //v = 0.6757780844554037
        //v = 0.8766453665342484
        //v = 0.22932628771811925
        //v = 0.7511241084480219
        //v = 0.4083960707924149
        //v = 0.04149314775749391
        //v = 0.21011728889136716
    }

    @Test
    public void testIntRandom(){
        System.out.println("请输入你要定义随机数的范围(a,b),例如“-5~6”");
        Scanner m = new Scanner(System.in);
        System.out.println("请输入负整数");
        int a = m.nextInt();
        System.out.println("a = " + a);
        System.out.println("请输入正整数");
        int b = m.nextInt();
        System.out.println("b = " + b);
        Random in = new Random();
        for (int i = 0; i < 5; i++) {
            int myrandom = in.nextInt(2);
            if (myrandom == 0) {
                int myrandom1 = in.nextInt(b + 1);
                // 正数
                System.out.println("你生成的随机数为" + myrandom1);
            } else {
                int myrandom1 = in.nextInt(a * (-1) + 1) * (-1);
                // 负数
                System.out.println("你生成的随机数为" + myrandom1);
            }
        }
        //请输入你要定义随机数的范围(a,b),例如“-5~6”
        //请输入负整数
        //-5
        //a = -5
        //请输入正整数
        //5
        //b = 5
        //你生成的随机数为0
        //你生成的随机数为3
        //你生成的随机数为-2
        //你生成的随机数为-5
        //你生成的随机数为2
    }

}
