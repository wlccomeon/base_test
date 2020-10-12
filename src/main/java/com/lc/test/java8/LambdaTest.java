package com.lc.test.java8;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @desc lambda表达式
 * 		本质：作为函数式接口的实例(只有一个抽象方法的接口)
 * @author wlc
 * @date 2020-01-11 10:50:16
 */
public class LambdaTest {


	public static void main(String[] args) {
		LambdaTest lambdaTest = new LambdaTest();
		lambdaTest.noParamReturn();

	}

	/**
	 * 无参，无返回值
	 * 直接运行方法体
	 */
	@Test
	public void noParamReturn(){
		//1.正常写法
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("我是lc1");
			}
		};
		r1.run();
		System.out.println("************");

		//2.lambda表达式
		Runnable r2 =() -> {System.out.println("我是lc2");};
		r2.run();
	}

	/**
	 * 一个参数，但没有返回值
	 */
	@Test
	public void oneParamNoReturn(){
		//普通写法
		Consumer<String> c1 = new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		};
		c1.accept("谎言和誓言的区别是什么呢？");
		System.out.println("*************");
		//lambda表达式写法1
		Consumer<String> c2 = (String s) -> {
			System.out.println(s);
		};
		c2.accept("一个是听得人当真了，一个是说的人当真了。");
		System.out.println("**************");
		//lambda表达式写法2，使用类型推断的形式，类似于泛型，省略参数的类型
		Consumer<String> c3 = (s) -> {
			System.out.println(s);
		};
		c3.accept("一个是虚情假意，一个是真心实意");
		System.out.println("**************");
		//lambda表达式写法3，如果参数只有一个，则小括号也可以省略掉
		Consumer<String> c4 = s -> {
			System.out.println(s);
		};
		c4.accept("一个是见异思迁，一个是矢志不移");
	}

	/**
	 * 两个或多个参数，多条执行语句，并且可以有返回值
	 */
	@Test
	public void multiParamAndReturn(){
		//普通写法
		Comparator<Integer> c1 = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				System.out.println(o1);
				System.out.println(o2);
				return o1.compareTo(o2);
			}
		};
		System.out.println(c1.compare(21, 12));
		System.out.println("**********************");
		//lambda表达式写法
		Comparator<Integer> c2 = (o1, o2) -> {
			System.out.println(o1);
			System.out.println(o2);
			return o1.compareTo(o2);
		};
		System.out.println(c2.compare(5, 9));
	}

	/**
	 * 当lambda体只有一条语句时，return与大括号若有，都可以省略
	 */
	@Test
	public void λOneSentence(){
		//普通写法
		Comparator<Integer> c1 = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
		System.out.println(c1.compare(21, 12));
		System.out.println("**********************");

		//λ表达式写法
		Comparator<Integer> c2 = (o1, o2) -> o1.compareTo(o2);
		System.out.println(c2.compare(5,9));
		Runnable r2 =() -> System.out.println("我是lc2");
		r2.run();
	}

}
