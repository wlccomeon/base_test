package com.lc.test.baseknowlege.collection;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        System.out.println("map = " + JSON.toJSONString(map));
    }

    /**
     * 将最后一个元素放入到第一个位置
     */
    @Test
    public void linkedListTest(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>(2);
        map.put("1","name1");
        map.put("2","name1");
        map.put("3","name1");
        map.put("4","name1");
        for (String s : map.keySet()) {
            System.out.println("s = " + s);
        }
        System.out.println("map = " + JSON.toJSONString(map));
        map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,LinkedHashMap::new));
        map.put("5","name5");
        System.out.println("mapNew = " + JSON.toJSONString(map));

    }
}
