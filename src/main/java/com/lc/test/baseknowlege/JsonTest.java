package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSONObject;
import com.lc.test.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class JsonTest {

    public static void main(String[] args) throws Exception {
//        testFastJson();
//        System.out.println("a="+"我熬");
//        String a = "  ";
//        if (StringUtils.isNotEmpty(a) && StringUtils.isBlank(a)){
//            System.out.println("a = " + "进来啦");
//        }
//        boolean emptyBlank = StringUtils.isEmpty(" ");
//        boolean blankBlank = StringUtils.isBlank(" ");
//        boolean blank2 = StringUtils.isBlank(" ");
//        System.out.println("emptyBlank = " + emptyBlank);
//        System.out.println("blank2 = " + blank2);
//        System.out.println("blankBlank = " + blankBlank);
        User user = new User();
        testObj(user);
        System.out.println("user = " + user);
    }

    static void testFastJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","123");
        System.out.println("fastJson1:"+jsonObject.toJSONString());
        //下面会报空指针的错误
        System.out.println("fastJson2:"+new JSONObject().put("key","123").toString());
    }

    static void testObj(User user) throws Exception {
        User realUser = new User();
        realUser.setAddress("aaaa");
        realUser.setId(1);
//        BeanUtils.copyProperties(user,realUser);
        user = realUser;
        System.out.println("realUser = " + realUser);
        System.out.println("testObj user = " + user);
    }

    
}
