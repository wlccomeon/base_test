package com.lc.test.baseknowlege.collection;

import java.util.LinkedHashMap;

/**
 * @author wlc
 * @description
 * @date 2021/3/12 0012 17:32
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {

        LinkedHashMap<String,String> map = new LinkedHashMap<>(2);
        map.put("1","name1");
        map.put("2","name1");
        map.put("3","name1");
        map.put("4","name1");
        for (String s : map.keySet()) {
            System.out.println("s = " + s);
        }
        System.out.println("map = " + map);
    }
}
