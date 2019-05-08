package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lc.test.entity.User;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
    /**
     * 目标：将120-1-A和120-2-A，120-1-B和120-2-B成对的排序到一起,而且每对中120-1必须在120-2前面
     * @param args
     */
    public static void main(String[] args) {
        //构造数据
        User user = new User();
        user.setId(1);
        user.setName("120-1-A");

        User user1 = new User();
        user1.setId(2);
        user1.setName("120-2-A");

        User user2 = new User();
        user2.setId(3);
        user2.setName("120-1-B");

        User user3 = new User();
        user3.setId(4);
        user3.setName("120-2-B");

        User user4 = new User();
        user4.setId(5);
        user4.setName("120-1-C");

        User user5 = new User();
        user5.setId(6);
        user5.setName("120-2-C");

        List<User> userList = new ArrayList<User>();
        //打乱顺序的赋值
        userList.add(user);
        userList.add(user2);
        userList.add(user5);
        userList.add(user3);
        userList.add(user4);
        userList.add(user1);
//        testListSort(userList);
        testCollectionSort(userList);
        System.out.println("最终得到的userList-->>"+JSON.toJSONString(userList));
        testListHalf(userList);
    }

    /**这个方法排序失败*/
    public static void testListSort(List<User> userList){
        System.out.println("原始list："+ JSON.toJSONString(userList));
        //根据name找到这A-A一对，B-B一对，C-C一对，最后组成一个list返回
        List<User> resultList = new CopyOnWriteArrayList<User>();
        for (User uFirst :userList){
            String nameFirst = uFirst.getName();
            String nameFirstSuffix= getSuffix(nameFirst);
            for (User uSecond : userList){
                String nameSecond = uSecond.getName();
                String nameSecondSuffix = getSuffix(nameSecond);
                if (!nameFirst.equals(nameSecond) && nameFirstSuffix.equals(nameSecondSuffix)){
                    Integer nameFirstSeq = Integer.parseInt(getStrSequence(nameFirst));
                    Integer nameSecondSeq = Integer.parseInt(getStrSequence(nameSecond));
                    //有序的添加成对的担保人
                    if (nameFirstSeq<nameSecondSeq){
                        if (resultList.size()>0){
                            for (User uThird:resultList){
                                String nameThird = uThird.getName();
                                if (nameThird.equals(nameFirst) || nameThird.equals(nameSecond)){

                                }else{
                                    resultList.add(uFirst);
                                    resultList.add(uSecond);
                                }
                            }
                        }else{
                            resultList.add(uFirst);
                            resultList.add(uSecond);
                        }
                    }else{
                        if (resultList.size()>0){
                            for (User uThird:resultList){
                                String nameThird = uThird.getName();
                                if (nameThird.equals(nameFirst) || nameThird.equals(nameSecond)){

                                }else{
                                    resultList.add(uSecond);
                                    resultList.add(uFirst);
                                }
                            }
                        }else{
                            resultList.add(uSecond);
                            resultList.add(uFirst);
                        }
                    }
                }
            }
        }
        System.out.println("结果list："+JSON.toJSONString(resultList));
    }

    public static String getSuffix(String str){
        String  midelLine= "-";
        String[] a = str.split(midelLine);
        String b = a[0]+midelLine+a[1]+midelLine;
        String[] c = str.split(b);
        String d = c[1];
        System.out.println("result--->"+d);
        return d;
    }
    public static String getStrSequence(String str){
        String  midelLine= "-";
        String[] a = str.split(midelLine);
        return a[1];
    }
    public static Integer getIntSequence(String str){
        String  midelLine= "-";
        String[] a = str.split(midelLine);
        return Integer.parseInt(a[1]);
    }

    /**
     * 重写Collections.sort方法，并得到120-1。。。120-2。。。排序的数据
     * @param userList
     */
    public static void testCollectionSort(List<User> userList){
        System.out.println("排序之前的结果："+JSON.toJSONString(userList));
        Collections.sort(userList, new Comparator<User>() {
            /*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            public int compare(User user1, User user2) {
//                if (getSuffix(user1.getName()).equals(getSuffix(user2.getName()))){
//                    return 0;
//                }
                if (getIntSequence(user1.getName())>getIntSequence(user2.getName())){
                    return 1;
                }
                return -1;
            }
        });
        System.out.println("排序之后的结果："+JSON.toJSONString(userList));
    }

    /**
     * 将120-1。。。120-2。。。排序的List分为两部分，再根据最后一个字母相等作为一对，重新组成一个List
     * @param userList
     */
    public static void testListHalf(List<User> userList){
        List<User> halfList1 = userList.subList(0,userList.size()/2);
        List<User> halfList2 = userList.subList(userList.size()/2,userList.size());
        System.out.println("半个List1为："+JSON.toJSONString(halfList1));
        System.out.println("半个List2为："+JSON.toJSONString(halfList2));

        List<User> entityList=new ArrayList<User>();
        for (int i=0; i<halfList1.size();i++){
            User uFir = halfList1.get(i);
            String nFirSuffix=getSuffix(uFir.getName());
            for (int j=0; j<halfList2.size();j++){
                User uSec = halfList2.get(j);
                String nSecSuffix=getSuffix(uSec.getName());
                if (nFirSuffix.equals(nSecSuffix)){
                    entityList.add(uFir);
                    entityList.add(uSec);
                }
            }
        }
        //SerializerFeature.DisableCircularReferenceDetect是为了避免打印出来的字符串为循环引用？？
        System.out.println("最终组装结果："+JSON.toJSONString(entityList, SerializerFeature.DisableCircularReferenceDetect));
    }
}
