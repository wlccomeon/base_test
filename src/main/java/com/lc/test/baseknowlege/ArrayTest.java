package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wlc on 2017/8/14 0014.
 */
public class ArrayTest {
    public static void main(String[] args) {
        testBubboSort2();
        testBubboSort();
//        testArrayAndSplit();
//        testArrayContains();
    }


    /**
     * 冒泡排序
     */
    public static void testBubboSort() {
        int[] sortArray = new int[]{1,7,3,2,9};
        //N个数字要排序完成，总共进行N-1趟排序，第i趟的排序次数为(N-i)次
        int sortNum = 0;
        //由于i和j从0开始，sortArray.length-1是为了防止数组越界
        for (int i = 0; i <sortArray.length-1 ; i++) {
            for (int j = 0; j < sortArray.length-1-i; j++) {
                sortNum++;
                int preInt = sortArray[j];
                int nextInt = sortArray[j+1];
                if (preInt>nextInt){
                    sortArray[j]=nextInt;
                    sortArray[j+1]=preInt;
                }
                System.out.println("第"+sortNum+"次排序,结果为:"+ Arrays.toString(sortArray));
            }
        }
    }
    /**
     * 冒泡排序,改进方法
     */
    public static void testBubboSort2() {
        int[] sortArray = new int[]{1,7,3,2,9};
        //N个数字要排序完成，总共进行N-1趟排序，第i趟的排序次数为(N-i)次
        int sortNum = 0;
        for (int i = 0; i <sortArray.length-1 ; i++) {
            //定义标记，如果在内层循环中全是正确的排列顺序，外层就不用循环了
            boolean flag = true;
            //第一次不执行，超过1次以后，进行判断，以期减少内部循环次数
            if (i>0){
                if (flag){
                    break;
                }
            }
            for (int j = 0; j < sortArray.length-1-i; j++) {
                sortNum++;
                int nextInt = sortArray[j+1];
                int preInt = sortArray[j];
                if (preInt>nextInt){
                    sortArray[j]=nextInt;
                    sortArray[j+1]=preInt;
                    flag = false;
                }
                System.out.println("第"+sortNum+"次排序,结果为:"+ Arrays.toString(sortArray));
            }
            if (flag){
                break;
            }
        }
    }

    public static void testArrayAndSplit(){
        String originStr = "9,10";
        String[] strings = StringUtils.split(originStr,",");
        int strLength=strings.length;
        System.out.println("strings.length"+strLength);
        for (int i = 0; i < strLength; i++) {
            String goalStr = strings[i];
            System.out.println("goalStr:"+goalStr);
            if (goalStr.equals("9")){
                System.out.println("出来了");
            }
        }
    }


    @Test
    public  void testArrayContains(){
        int[] nums = {1,2,3,4,5,6,7,8};
        List numsList = Arrays.asList(nums);
        System.out.println("numsList-->>"+numsList);
        List myList = Arrays.asList(1,2,3,4,5,6);
        System.out.println("myuList-->>"+ JSON.toJSONString(myList));
        System.out.println("list contains result-->>>"+myList.contains(2));

        int aa = Arrays.binarySearch(nums,10);
        System.out.println("aa result--->>"+aa);
    }
}
