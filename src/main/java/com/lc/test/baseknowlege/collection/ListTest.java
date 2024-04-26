package com.lc.test.baseknowlege.collection;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.User;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * List集合测试
 * @author wlc
 */
@Slf4j
public class ListTest {

    private static List<String> list = new ArrayList<>();
    static {
        list.add("add");
        list.add("delete");
        list.add("delete");
        list.add("update");
        list.add("query");
    }

    /**
     * 取两个list的交集
     */
    @Test
    public void retainTest(){
        List<String> myList = new ArrayList<>();
        myList.addAll(Arrays.asList("add","select","query"));
        list.retainAll(myList);
        //result ： add、query
        System.out.println("list = " + list);
    }

    @Test
    public void changeTest(){
        List<User> originUsers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User user = new User();
            user.setId(i);
            user.setName("wlc"+i);
            originUsers.add(user);
        }
        List<User> newUsers = new ArrayList<>();
        newUsers.addAll(originUsers);
        System.out.println("JSON.toJSONString(newUsers) first = " + JSON.toJSONString(newUsers));
        System.out.println("originUsers = " + JSON.toJSONString(originUsers));
        originUsers.remove(0);
        System.out.println("JSON.toJSONString(newUsers) second= " + JSON.toJSONString(newUsers));
        System.out.println("JSON.toJSONString(originUsers) = " + JSON.toJSONString(originUsers));
        //结果，addall方法加入的数据不会受原始数据影响
    }

    @Test
    public void distinctTest(){
        List<String> distinctList = list.stream().distinct().collect(Collectors.toList());
        distinctList.forEach(System.out::println);
    }

    @Test
    public void filterTest(){
        List<String> collect = list.stream().filter(str -> "ddd".equals(str)).collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }

    @Test
    public void copyTest(){
        List<User> nowList = new ArrayList<>();
        User user = new User();
        user.setAddress("aaa");
        user.setId(1);
        nowList.add(user);

        User user2 = new User();
        user2.setAddress("bbb");
        user2.setId(2);

        BeanUtils.copyProperties(user2,user);
        System.out.println("nowList = " + nowList);
    }

    @Test
    public void subListTest(){
        List<String> strings = list.subList(0, 2);
        strings.forEach(System.out::println);
        System.out.println("==================");
        list.forEach(System.out::println);

    }

    @Test
    public void addTest(){
        AtomicLong totalGiftPrice = new AtomicLong(0);
        Long price = 1000L;
        System.out.println("totalGiftPrice.get() = " + totalGiftPrice.get());
        System.out.println("totalGiftPrice.addAndGet(price) = " + totalGiftPrice.addAndGet(price));

    }
        
    @Test
    public void assembleStrTest(){
        String key = "IEG_ANCHOR_CHALLENGE_TASK_HIT_AWARD:%s:%s:%s";
        System.out.println("String.format(key,1,2) = " + String.format(key, 1, 2,""));
        System.out.println("String.format(key,1,2,3) = " + String.format(key, 1, 2, 3));
        System.out.println("String.format(\"aaaaa\",2,3) = " + String.format("aaaaa", 2, 3));
    }
    
    @Test
    public void indexOfTest(){
        int index = list.indexOf("add");
        System.out.println("index = " + index);
        System.out.println("list1 = " + list);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if (next.equals("update")){
                iterator.remove();
            }
        }
//        if (index>=0){
//            list.remove(index);
//        }
        System.out.println("list2 = " + list);
    }

    @Test
    public void strTest(){
        String a = "update,delete,select";
        String b = "select";
        System.out.println("a.contains(b) = " + a.contains(b));
    }

    /**
     * 遍历时删除
     */
    @Test
    public void removeWhileTraverse(){

//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()){
//            String next = iterator.next();
//            if ("delete".equals(next)){
//                iterator.remove();
//            }
//        }
//        list.forEach(System.out::println);

        for (String s : list) {
            if ("delete".equals(s)){
                list.remove(s);
            }
        }
    }

    @Test
    public void collectionTest(){
        printCollection(list);
        System.out.println("=============");
        list.add(0,"first");
        printCollection(list);
        System.out.println("=============");
        Set<String> strSet = new HashSet<>();
        strSet.addAll(list);
        //不允许添加null
//        strSet.addAll(null);
        printCollection(strSet);



    }

    @Test
    public void sourceCodeTest(){
        List<String> myList = new ArrayList<>();
    }

    private void printCollection(Collection<String> collection){
        collection.forEach(System.out::println);
    }

	@Test
    public void copyListTest(){
        List<String> copyList = new ArrayList<>();
        copyList.addAll(list);
        System.out.println("==================");
        copyList.forEach(System.out::println);
        List<String> subList = copyList.subList(0,2);
        System.out.println("==================");
        subList.forEach(System.out::println);
        System.out.println("==================");
        list.forEach(System.out::println);
        List<String> subList1 = list.subList(0, 2);
        System.out.println("==================");
        subList1.forEach(System.out::println);
        System.out.println("==================");
        //sublist的删除会导致原list元素的删除
        subList1.remove(1);
        list.forEach(System.out::println);

    }

}
