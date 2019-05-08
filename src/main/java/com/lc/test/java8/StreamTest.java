package com.lc.test.java8;

import com.google.common.collect.Lists;

import java.util.List;

public class StreamTest {
    public static void main(String[] args) {
        testFilter();
    }

    public static void testFilter(){
        List<Integer> nums = Lists.newArrayList(null,null);
        System.out.println(nums.stream().filter(num -> num !=null).count());
    }
}
