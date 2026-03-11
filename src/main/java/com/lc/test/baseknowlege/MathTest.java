package com.lc.test.baseknowlege;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wlc
 * @desc
 * @date 2026/2/5 星期四
 */
public class MathTest {


    @Test
    public void testMin(){
        Integer a = 1;
        Integer b = 2;
        a = Math.max(a,b);
        System.out.println("a = " + a);
    }

    @Test
    public void testEmptyListLimit(){
        List<Integer> list = Arrays.asList(1,2,3);
        //limit中如果为-1，则报错。为大于-1的整数情况下都不报错。不论list是否为空。
        list.stream().limit(-1).forEach(System.out::println);
    }

}
