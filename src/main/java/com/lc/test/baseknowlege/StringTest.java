package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class StringTest {

    public static void main(String[] args) {
//        testStr();
//        testSubString();
//        testRemoveSpace();
//        User user = new User();
//        user.setName("   吴利昌  哈哈   ");
//        user.setId(1);
//        testDeliveryParam(user);
//        System.out.println("最终的user结果为-->>"+ JSON.toJSONString(user));
//        testStrParam();
//        getNewStringBuider();
        analysisTime();
    }

    public static void testSubString(){
        String aa = ",haha,lc";
        String bb = aa.substring(aa.indexOf(",")+1,aa.length());
        String cc = aa.substring(0,aa.indexOf(","));
        int dd = aa.indexOf(",");
        int ee = aa.indexOf("-");
        System.out.println("bb:"+bb);
        System.out.println("cc:"+cc);
        System.out.println("dd:"+dd);
        System.out.println("ee:"+ee);

    }

    public static void testRemoveSpace(){
        String aa = "    hahaha lc  ";
        String bb = aa.trim();
        System.out.println("aa-->>"+aa+"去除空格之后的结果bb-->>"+bb);
    }

    public static void testDeliveryParam(User user){
        String originName = user.getName();
        System.out.println("原始name-->>"+originName);
        String removeSpaceName = originName.trim();
        System.out.println("去除空格之后的name-->>"+removeSpaceName);
    }

    public static void testStr(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("a","11111");
        Integer b = Integer.parseInt(map.get("a").toString());
        System.out.println("b=="+b);
    }

    /**
     * 基本类型的包装类作为参数传递，效果和基本类型作为参数传递时一样的
     * 而其他引用类型如Map、StringBuilder、List等，形参的改变会直接影响实际参数
     */
    public static void testStrParam(){
        String s1 = "hello";
        String s2 = "world";
        System.out.println(s1+"--------"+s2);
        change(s1,s2);
        System.out.println(s1+"--------"+s2);
        StringBuilder sb1 = new StringBuilder("hello");
        StringBuilder sb2 = new StringBuilder("world");
        System.out.println(sb1+"--------"+sb2);
        change(sb1,sb2);
        System.out.println(sb1+"--------"+sb2);
        Integer i1 = 111;
        Integer i2 = 222;
        System.out.println(i1+"--------"+i2);
        change(i1,i2);
        System.out.println(i1+"--------"+i2);
    }

    public static void change(String s1,String s2){
        s1 = s2;
        s2 = s1+s2;
    }
    public static void change(StringBuilder sb1,StringBuilder sb2){
        //sb2为sb1赋值的时候改变的是缓冲区的值，不是真正的改变了sb1的值
        //而使用append方法之后，sb2就真正的改变了
        sb1 = sb2;
        sb2 = sb2.append(sb1);
    }
    public static void change(Integer i1,Integer i2){
        i1 = i2;
        i2=i1+i2;
    }

    public static void getNewStringBuider(){
        StringBuilder result = new StringBuilder();
        StringBuilder firstJoin = new StringBuilder();
        for (int i=0;i<5;i++){
            firstJoin.append("iYa"+i+" or ");
        }
        System.out.println("firstJoin-->>"+firstJoin.toString());
        System.out.println("result-->>"+result.append(firstJoin.substring(0,firstJoin.lastIndexOf("or")-1)));
    }

    public static void analysisTime(){
        String originStr = "2天20小时32分";
        String time2 = originStr.replace(" ","");
        //        String time1 = "2天20小时32分";
        String time1 = time2;
        int dayLocation = time1.indexOf("天");
        int hourLocation = time1.indexOf("小时");
        int minuteLocation = time1.indexOf("分");

        Long dayMinutes = 0L;
        Long hourMinutes = 0L;
        Long minutes = 0L;

        if (dayLocation>=0){
            String dayStr = time1.substring(0,dayLocation);
            dayMinutes = Integer.parseInt(dayStr)*24*60L;
        }
        if (hourLocation>=0){
            String hourStr = "";
            if (dayLocation>=0){
                hourStr = time1.substring(dayLocation+1,hourLocation);
            }else{
                hourStr = time1.substring(0,hourLocation);
            }
            hourMinutes = Integer.parseInt(hourStr)*60L;
        }

        if (minuteLocation>=0){
            String minuteStr = "";
            if (hourLocation>=0){
                minuteStr = time1.substring(hourLocation+2,minuteLocation);
            }else{
                minuteStr = time1.substring(0,minuteLocation);
            }
            minutes = Long.parseLong(minuteStr);
        }

        Long totalMinutes = dayMinutes + hourMinutes + minutes;
        System.out.println("totalMinutes result -->>"+totalMinutes);
    }
}
