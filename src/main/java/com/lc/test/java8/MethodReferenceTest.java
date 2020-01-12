package com.lc.test.java8;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.Weigher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @desc 方法引用（基于λ表达式）
 * 		1.使用场景： 当要传递给λ体的操作，已经有实现的方法了，可以使用方法引用
 * 		2.方法引用，本质上就是λ表达式，而λ表达式作为函数式接口的实例。所以，方法引用也是函数式接口的实例
 *      3.使用格式： 类(或对象) :: 方法名
 *      4.具体分为如下的三种情况：
 *      	对象 :: 非静态方法
 *          类 :: 静态方法
 *          类 :: 非静态方法
 *      5.使用要求： 要求接口中的抽象方法的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型相同
 * @author wlc
 * @date 2020-01-11 13:34:20
 */
public class MethodReferenceTest {
	/**
	 * Consummer中的 void accept(T t)
	 * PrintStream中的void println(T t)
	 */
	@Test
	public void test1(){
		//普通λ表达式
		Consumer<String> c1 = s -> System.out.println(s);
		c1.accept("lc1");
		System.out.println("*************");
		//方法引用
		PrintStream ps = System.out;
		Consumer<String> c2 = ps::println;
		c2.accept("lc2");
	}
	/***
	 * Supplier中的 T get()
	 * Employee中的 String getName()(这里使用了Lombok的原因，不显示该方法，但是可以调用)
	 */
	@Test
	public void test2(){
		Employee emp = new Employee("lc",21,new BigDecimal(100000));
		//普通λ表达式
		Supplier<String> supplier = ()->emp.getName();
		System.out.println(supplier.get());
		//方法引用:get值
		Supplier<String> supplier1 = emp::getName;
		System.out.println(supplier1.get());
		//方法引用：set值
		Consumer<String> consumer = emp::setName;
		consumer.accept("lcnew");
		System.out.println(emp.getName());
	}

	/**
	 * 类::静态方法
	 * Comparator中的int compare(T t1,T t2)
	 * Integer中的int compare(T t1,T t2)
	 */
	@Test
	public void test3(){
		//普通λ表达式
		Comparator<Integer> c1 = (i1, i2) -> Integer.compare(i1, i2);
		System.out.println(c1.compare(1,2));
		System.out.println("****************");
		//方法引用
		Comparator<Integer> c2 = Integer::compare;
		System.out.println(c2.compare(2,1));
	}

	/**
	 * Function中的 R apply(T t)
	 * Math中的Long round(double d)
	 */
	@Test
	public void test4(){
		//普通写法
		Function<Double,Long> normalFunc = new Function<Double, Long>() {
			@Override
			public Long apply(Double d) {
				return Math.round(d);
			}
		};
		System.out.println(normalFunc.apply(11.2));
		System.out.println("***********");
		//λ表达式写法
		Function<Double,Long> λFunc = d -> Math.round(d);
		System.out.println(λFunc.apply(12.5));
		System.out.println("***********");
		//方法引用写法
		Function<Double,Long> methodRefFunc = Math::round;
		System.out.println(methodRefFunc.apply(13.56));

	}

	/***
	 * 类::实例方法(不好想到)
	 * Comparator中的int compare(T t1,T t2)
	 * String中的int t1.compareTo(t2)
	 * 虽然参数个数不一致，但t1作为调用方，所以可以使用方法引用
	 */
	@Test
	public void test5(){
		Comparator<String> c1 = (s1,s2) -> s1.compareTo(s2);
		System.out.println(c1.compare("ab", "ac"));
		System.out.println("***********");
		Comparator<String> c2 = String::compareTo;
		System.out.println(c2.compare("cd","ab"));
		//BiPredicate<String,String> 中的 boolean test(T t1,T t2)与String中的 boolean t1.equals(t2)
		BiPredicate<String,String> pre1 = (s1,s2) -> s1.equals(s2);
		System.out.println(pre1.test("ef","eg"));
		System.out.println("*************");
		BiPredicate<String,String> pre2 = String::equals;
		System.out.println(pre2.test("ad","ad"));
		System.out.println(pre2.test(" ","  "));
		//Function中的 R apply(T t) 与 Employee中的 String getName()
		Employee emp = new Employee("lc",22,new BigDecimal(20000));
		Function<Employee,String> func1 = employee -> employee.getName();
		System.out.println(func1.apply(emp));
		System.out.println("***************");
		Function<Employee,String> func2 = Employee::getName;
		System.out.println(func2.apply(emp));
	}

	/**定义一个内部类*/
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Employee{
		private String name;
		private Integer age;
		private BigDecimal salary;
	}

}

