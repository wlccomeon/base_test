package com.lc.test.baseknowlege;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class BigDecimalTest {

    @Test
    public void calculateTest(){
        BigDecimal unitAwardAmount = new BigDecimal(255)
                .multiply(new BigDecimal(10))
                .divide(new BigDecimal(258),2,BigDecimal.ROUND_DOWN);
        System.out.println("unitAwardAmount = " + unitAwardAmount);
    }


    @Test
    public void matchTest(){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]{1,9}");
        if(!pattern.matcher("123AA打开的").matches()){
            System.out.println("\"请填写9位护照号\" = " + "请填写9位护照号");
        }else{
            System.out.println("\"正确\" = " + "正确");
        }
    }


    @Test
    public void testZero(){
        BigDecimal a = new BigDecimal("0.200");
        BigDecimal b = new BigDecimal("0.2");
        System.out.println("a.compareTo(b) = " + a.compareTo(b));
    }

    @Test
    public void testDivide(){
        BigDecimal a = new BigDecimal("910");
        BigDecimal b = new BigDecimal("3667.52");
        BigDecimal result = a.divide(b,10,RoundingMode.HALF_UP);
        System.out.println("result = " + result);
        Double c = 500.49;
        BigDecimal d = BigDecimal.valueOf(c);
        BigDecimal e = d.divide(BigDecimal.valueOf(10000),4,RoundingMode.HALF_UP);
        System.out.println("e = " + String.valueOf(e));
    }

    @Test
    public void testCompareDouble(){
        BigDecimal a = new BigDecimal(0.10);
        boolean result = a.compareTo(new BigDecimal(0.1))>=0;
        System.out.println("result = " + result);
    }

    @Test
    public void testDivide2(){
        BigDecimal overdueCountD7M6B = new BigDecimal(2);
        BigDecimal timesM6B = new BigDecimal(12);
        BigDecimal calcResult = overdueCountD7M6B.divide(timesM6B,2,RoundingMode.HALF_UP);
        System.out.println(calcResult);
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
