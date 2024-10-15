package com.lc.test.baseknowlege;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author wlc
 * @desc: DoubleTest
 * @datetime: 2024/7/16 0016 10:40
 */
public class DoubleTest {

    @Test
    public void testDoubleZero(){
        double zero = 0.0000001;
        double zero2 = 0.0000000000000;
        if (Math.abs(zero) == 0){
            System.out.println("zero == 0");
        }else {
            System.out.println("zero != 0");
        }
        int i = new BigDecimal(zero2).compareTo(BigDecimal.ZERO);
        System.out.println("i = " + i);
    }
    @Test
    public void testDoubleCompareInt(){
        Double a = 2.0D;
        boolean b = a==2;
        System.out.println("b = " + b);
        boolean c = a.equals(2);
        System.out.println("c = " + c);
    }




}

