package com.lc.test.baseknowlege;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wlc on 2017/9/16 0016.
 */
public class ReplaceTest {
    public static void main(String[] args) {
        String myPhoneNum = "15076637766";
        StringUtils.isNotBlank(myPhoneNum);
        String replaceFirstStr =  myPhoneNum.replaceFirst("\\d[1]","h");
        String replaceAllStr = myPhoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$1");
        System.out.println("myPhoneNum:"+myPhoneNum+"\n"+"replaceFirstStr:"+replaceFirstStr+"\n"+"replaceAllStr:"+replaceAllStr);
    }
}
