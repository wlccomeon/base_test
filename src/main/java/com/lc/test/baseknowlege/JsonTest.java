package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSONObject;

public class JsonTest {

    public static void main(String[] args) {
        testFastJson();
    }

    static void testFastJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","123");
        System.out.println("fastJson1:"+jsonObject.toJSONString());
        //下面会报空指针的错误
        System.out.println("fastJson2:"+new JSONObject().put("key","123").toString());
    }
}
