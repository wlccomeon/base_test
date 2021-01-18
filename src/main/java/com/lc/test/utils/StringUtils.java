package com.lc.test.utils;

/**
 * @author wlc
 * @description
 * @date 2021/1/7 0007 14:27
 */
public class StringUtils {
    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    private static String firstLetterToUpper(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        // 偏移之前需要判断是否为小写字母
        char[] cs=str.toCharArray();
        char c = cs[0];
        if (c >= 'a' && c<= 'z'){
            cs[0] = (char)(c-32);
        }
        return String.valueOf(cs);
    }
}
