package com.lc.test.utils;

import java.io.*;

/**
 * @author wlc
 * @desc: FileUtils
 * @datetime: 2023/10/31 0031 17:24
 */
public class FileUtils {
    /**
     * 读取文件内容，并把内容作为字符串返回
     * @param f 要读取的文件
     * @return 字符串形式的文件内容。
     */
    public static String readAsString(File f) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String str = br.readLine();
            while (null != str) {
                sb.append(str).append("\n");
                str = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                    br = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

