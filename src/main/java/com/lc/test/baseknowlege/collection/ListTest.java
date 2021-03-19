package com.lc.test.baseknowlege.collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import javax.servlet.http.HttpUtils;
import java.util.*;

/**
 * List集合测试
 * @author wlc
 */
public class ListTest {

    private static List<String> list = new ArrayList<>();
    static {
        list.add("add");
        list.add("delete");
        list.add("delete");
        list.add("update");
        list.add("query");
    }


    @Test
    public void indexOfTest(){
        int index = list.indexOf("update2");
        System.out.println("index = " + index);
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
