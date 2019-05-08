package com.lc.test.baseknowlege;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        testContainsKey();
    }

    public static void testContainsKey(){
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("a",1);
        dataMap.put("b",2);
        if (dataMap.containsKey(null)){
            System.out.println("false");
        }else{
            System.out.println("true");
        }
    }
}
