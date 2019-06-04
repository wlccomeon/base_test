package com.lc.test.baseknowlege;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CashTest {
    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        System.out.println(decimalFormat.format(100000022));    //1,002,200,999.22
        showStringByDate(new Date(),new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"),"aaa");
    }

    public static String showStringByDate(Date date, SimpleDateFormat fmt,String test){
        if (date == null){
            System.out.println("date为空！");
            return null;
        }else{
            try {
                System.out.println("时间格式为-->>"+fmt.format(date));
                return fmt.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
