package com.lc.test.java8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @desc Java内置的4大核心函数式接口(当然也还有其他类型的)(基于λ表达式)
 * 		消费型接口：Comsummer<T>    void accept(T t)
 * 		供给型接口：Supplier<T>     T get()
 * 		函数型接口：Function<T>     R apply(T t)
 * 		断定型接口：Predicate<T>    boolean test(T t)
 * @author wlc
 * @date 2020-01-11 13:03:01
 */
public class FunctionalInterfaceTest {
	/**
	 * 断定函数式接口
	 */
	@Test
	public void predicateTest(){
		List<String> strings = Arrays.asList("北京", "南京", "东京", "上海", "广州", "上京");
		//过滤出包含“京”的所有字符串，这样将实现逻辑放在了调用方
		List<String> filterResult = filterString(strings, s -> s.contains("京"));
		System.out.println(filterResult);
	}

	@Test
	public void comsummerTest(){

	}


	/**
	 * 一个过滤方法（需要在调用方实现具体的过滤逻辑）
	 * @param list  字符串集合
	 * @param predicate 断定接口
	 */
	public List<String> filterString(List<String> list, Predicate<String> predicate){
		List<String> result = new ArrayList<>();
		for (String s : list) {
			if (predicate.test(s)){
				result.add(s);
			}
		}
		return result;
	}

}
