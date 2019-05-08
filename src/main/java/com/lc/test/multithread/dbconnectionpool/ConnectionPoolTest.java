package com.lc.test.multithread.dbconnectionpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 模拟客户端Connectionrunner获取、使用、释放连接的过程，对数据进行统计
 */
public class ConnectionPoolTest {

    private final List<Object> list = Collections.synchronizedList(new ArrayList<>());
    static String myPath = "aa.aa";

    public static void main(String[] args) {
        if (myPath.endsWith("aa")){
            System.out.println("哈哈哈哈哈");
        }
    }


}
