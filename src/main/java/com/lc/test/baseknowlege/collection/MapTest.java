package com.lc.test.baseknowlege.collection;

import com.google.common.collect.Lists;
import com.lc.test.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wlc
 * @description
 * @date 2020/12/23 0023 11:05
 */
public class MapTest {

    @Test
    public void List2MapByLambda(){
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("张三");
        user1.setAddress("丰台");
        User user2 = new User();
        user2.setName("李四");
        user2.setAddress("丛台");
        User user3 = new User();
        user3.setName("王五");
        user3.setAddress("邢台");
        User user5 = new User();
        user5.setName("张三");
        user5.setAddress("兰陵");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user5);

        //因为张三有多个address，会报java.lang.IllegalStateException: Duplicate key 丰台 错误
//        Map<String, String> mapResult0 = userList.stream().collect(
//                Collectors.toMap(User::getName, User::getAddress)
//        );
//        System.out.println("mapResult0 = " + mapResult0.toString());

        Map<String, String> mapResult1 = userList.stream().collect(
                Collectors.toMap(User::getName, User::getAddress, (n1, n2) -> n1+n2)
        );
        //mapResult1 = {李四=丛台, 张三=丰台兰陵, 王五=邢台}
        System.out.println("mapResult1 = " + mapResult1.toString());

        Map<String, String> mapResult2 = userList.stream().collect(
                Collectors.toMap(User::getName, User::getAddress, (n1, n2) -> n1)
        );
        //mapResult1 = {李四=丛台, 张三=丰台, 王五=邢台}
        System.out.println("mapResult2 = " + mapResult2.toString());
    }

    @Test
    public void convertToMapTest(){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("1","A");
        paramMap.put("2","B");
        this.convert(paramMap);
    }

    public <T> void  convert(T param){
        Map<String,String> result = (Map<String,String>)param;
        System.out.println("result = " + result);
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
//        // 获得当前日期是一个星期的第几天
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if (1 == dayWeek) {
//            cal.add(Calendar.DAY_OF_MONTH, -1);
//        }
//        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        // 获得当前日期是一个星期的第几天
//        int day = cal.get(Calendar.DAY_OF_WEEK);
//        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        System.out.println("所在周星期一的日期：" + sdf.format(cal.getTime()));
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        //获取北京时间所在的日期
//        LocalDate today = LocalDate.now(ZoneOffset.ofHours(8));
//        //获取本周周一日期和周日日期
//        LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
//        String result = monday.format(formatter);
//        System.out.println("result = " + result);

        Map<String,Object> map = new HashMap<>();
        System.out.println("CollectionUtils.isEmpty(map) = " + CollectionUtils.isEmpty(map));

    }
}
