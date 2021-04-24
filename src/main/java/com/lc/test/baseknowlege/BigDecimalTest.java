package com.lc.test.baseknowlege;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {

    @Test
    public void calculateTest(){
        BigDecimal unitAwardAmount = new BigDecimal(255)
                .multiply(new BigDecimal(10))
                .divide(new BigDecimal(258),2,BigDecimal.ROUND_DOWN);
        System.out.println("unitAwardAmount = " + unitAwardAmount);
    }

    @Test
    public void HalfUpAndDownTest(){
        BigDecimal a = new BigDecimal("2.137");
        BigDecimal b = new BigDecimal("2.132");
        BigDecimal aHalfDown = a.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal bHalfDown = b.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println("aHalfDown = " + aHalfDown);
        System.out.println("bHalfDown = " + bHalfDown);

        BigDecimal aHalfUp = a.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal bHalfUp = b.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println("aHalfUp = " + aHalfUp);
        System.out.println("bHalfUp = " + bHalfUp);

        BigDecimal aDown = a.setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal bDown = b.setScale(2,BigDecimal.ROUND_DOWN);
        System.out.println("aDown = " + aDown);
        System.out.println("bDown = " + bDown);

        BigDecimal aUp = a.setScale(2, BigDecimal.ROUND_UP);
        BigDecimal bUp = b.setScale(2,BigDecimal.ROUND_UP);
        System.out.println("aUp = " + aUp);
        System.out.println("bUp = " + bUp);

        //aHalfDown = 2.14
        //bHalfDown = 2.13
        //aHalfUp = 2.14
        //bHalfUp = 2.13
        //aDown = 2.13
        //bDown = 2.13
        //aUp = 2.14
        //bUp = 2.14

    }

}
