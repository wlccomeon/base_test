package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlc on 2018/3/6 0006.
 */
public class MainTest {
	public static void main(String[] args) {
		//main
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		//test
		test();
		//main
		System.out.println("getPreMethodName() = " + getPreMethodName());
	}
	public static void test(){
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * 获取调用本方法的上一级方法名
	 * @return
	 */
	public static String getPreMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

}
class Test{
	public static void main(String[] args) {
		test2("aaaa");
	}
	public static void test2(String param){
		System.out.println(param.getClass().getName());
		System.out.println(param.getClass().getTypeName());
		System.out.println("MainTest.getPreMethodName() = " + MainTest.getPreMethodName());
		final String methodName = new Exception().getStackTrace()[0].getMethodName();
		System.out.println("methodName = " + methodName);
	}
}
