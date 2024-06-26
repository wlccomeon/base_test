package com.lc.test.utils;

import java.io.File;
import java.util.regex.Pattern;

/**
 * 字符串中存在 反斜杠+u 开头 的Unicode字符。本类用于把那些Unicode字符串转换成汉字
 * @author 张超
 *
 */
public final class UicodeBackslashU {
    // 单个字符的正则表达式
    private static final String singlePattern = "[0-9|a-f|A-F]";
    // 4个字符的正则表达式
    private static final String pattern = singlePattern + singlePattern +
            singlePattern + singlePattern;

    public static void main(String[] args) {
        //String json = "{\n" +
        //        "    \"msg\":\"success\",\n" +
        //        "    \"data\":{\n" +
        //        "        \"userId\":\"12363324\",\n" +
        //        "        \"collegeName\":\"\\u8BA1\\u7B97\\u673A\\u5B66\\u9662\",\n" +
        //        "        \"className\":\"\\u8F6F\\u4EF6\\u4E00\\u73ED\"\n" +
        //        "         \"test\":\"\\u6B21\\u4EFB\\u52A1\\u65E0\\u6CD5\\u5904\\u7406\\uFF0C\\u4EFB\\u52A1\\u5904\\u7406\\u4EBA\\u4E0D\\u4E00\\u81F4\"\n" +
        //        "    }\n" +
        //        "}\n";
        String json = FileUtils.readAsString(new File("src/test/resources/MyJson.json"));
        String str = UicodeBackslashU.unicodeToCn(json);
        System.out.println("str = " + str);
    }


    /**
     * 把 \\u 开头的单字转成汉字，如 \\u6B65 ->　步
     * @param str
     * @return
     */
    private static String ustartToCn(final String str) {
        StringBuilder sb = new StringBuilder().append("0x")
                .append(str.substring(2, 6));
        Integer codeInteger = Integer.decode(sb.toString());
        int code = codeInteger.intValue();
        char c = (char)code;
        return String.valueOf(c);
    }

    /**
     * 字符串是否以Unicode字符开头。约定Unicode字符以 \\u开头。
     * @param str 字符串
     * @return true表示以Unicode字符开头.
     */
    private static boolean isStartWithUnicode(final String str) {
        if (null == str || str.length() == 0) {
            return false;
        }
        if (!str.startsWith("\\u")) {
            return false;
        }
        // \u6B65
        if (str.length() < 6) {
            return false;
        }
        String content = str.substring(2, 6);

        boolean isMatch = Pattern.matches(pattern, content);
        return isMatch;
    }

    /**
     * 字符串中，所有以 \\u 开头的UNICODE字符串，全部替换成汉字
     * @param strParam
     * @return
     */
    public static String unicodeToCn(final String str) {
        // 用于构建新的字符串
        StringBuilder sb = new StringBuilder();
        // 从左向右扫描字符串。tmpStr是还没有被扫描的剩余字符串。
        // 下面有两个判断分支：
        // 1. 如果剩余字符串是Unicode字符开头，就把Unicode转换成汉字，加到StringBuilder中。然后跳过这个Unicode字符。
        // 2.反之， 如果剩余字符串不是Unicode字符开头，把普通字符加入StringBuilder，向右跳过1.
        int length = str.length();
        for (int i = 0; i < length;) {
            String tmpStr = str.substring(i);
            if (isStartWithUnicode(tmpStr)) { // 分支1
                sb.append(ustartToCn(tmpStr));
                i += 6;
            } else { // 分支2
                sb.append(str.substring(i, i + 1));
                i++;
            }
        }
        return sb.toString();
    }
}


