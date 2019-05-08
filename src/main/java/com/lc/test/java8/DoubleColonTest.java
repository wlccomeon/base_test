package com.lc.test.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 双冒号的用法
 * 就是把方法当做参数传到stream内部，使stream的每个元素都传入到该方法里面执行一下。
 * @author wlc
 */
public class DoubleColonTest {
    public static void main(String[] args) {
        List<String> al = Arrays.asList("a", "b", "c", "d");
        al.forEach(AcceptMethod::printValur);
        //下面的方法和上面等价的
        //方法参数
        Consumer<String> methodParam = AcceptMethod::printValur;
        //方法执行accept
        al.forEach(x -> methodParam.accept(x));
    }
}
