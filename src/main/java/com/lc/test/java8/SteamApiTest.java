package com.lc.test.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @desc
 * Stream API主要处理集合，可以进行复杂的查找、过滤、映射数据等操作
 *           处理集合的过程类似于使用SQL执行的数据库查询
 *   Stream与Collection的区别：
 *       Collection是一种静态的内存数据结构，而Stream是有关计算的。
 *       前者主要面向内存，后者主要面向CPU进行实时计算
 *   Stream：
 *       概念：
 *   	     是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列；
 *   	     Stream自己不会存储对象；
 *   	     Stream不会改变源对象，相反，他们会返回一个持有结果的新Stream；
 *   	     Stream的操作是延迟执行的，也就是说他们会等到需要结果的时候才会执行。
 *       操作步骤：
 *           1.创建Stream，如集合、数组，获取一个流
 *           2.中间操作，对数据源的数据进行处理，如过滤、映射等
 *           3.终止操作：一旦操作终止，会产生结果。所创建的流将不能再被利用。
 * @author wlc
 * @date 2020-01-11 18:16:28
 */
public class SteamApiTest {

	/**
	 * 创建Stream
	 */
	@Test
	public void test1(){
	//方式一:通过集合
		List<Employee> employees = getEmployees();
		//返回一个顺序流
		Stream<Employee> sequenceStream = employees.stream();
		//返回一个并行流
		Stream<Employee> parallelStream = employees.parallelStream();
	//方式二：通过数组
		int[] arrayData = {1,2,3,4,65};
		IntStream intStream = Arrays.stream(arrayData);
		//创建一个泛型Stream
		Employee emp1 = new Employee(103, "白永祥", 39, 1220.02);
		Employee emp2 = new Employee(104, "卢冰伟", 28, 20388.08);
		Employee[] emps = new Employee[]{emp1,emp2};
		Stream<Employee> employeeStream = Arrays.stream(emps);
	//方式三：通过Stream的of()方法
		Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7);
	}

	/**
	 * 筛选与切片
	 */
	@Test
	public void test2(){
		List<Employee> employees = getEmployees();
		//查询员工中薪资大于1万的员工信息
		employees.stream().filter(employee -> employee.getSalary()>10000).forEach(System.out::println);
		System.out.println("*************");
		//limit(n)截断流，使其元素不能超过给定数量
		employees.stream().limit(3).forEach(System.out::println);
		System.out.println("*************");
		//skip(n)-跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n)互补
		employees.stream().skip(3).forEach(System.out::println);
		System.out.println("*************");
		//distinct---筛选，通过流所生成的元素的hashcode()和equals()去除重复元素
		employees.add(new Employee(106,"常程",42,6030.66));
		employees.add(new Employee(106,"常程",42,6030.66));
		employees.stream().distinct().forEach(System.out::println);
	}

	/**
	 * 映射
	 */
	@Test
	public void test3(){
		//map(Function f)---接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素
		List<String> strings = Arrays.asList("aa", "bb", "cc", "dd");
		strings.stream().map(str->str.toUpperCase()).forEach(System.out::println);
		System.out.println("****************");
		//case1：获取员工姓名长度大于3的员工的姓名
		List<Employee> employees = getEmployees();
//		employees.stream().map(employee -> employee.getName().length()>3).forEach(System.out::println); //这个结果就是打印的一堆false，因为没有符合的记录
		Stream<String> nameStream = employees.stream().map(Employee::getName);
		nameStream.filter(name -> name.length()>2).forEach(System.out::println);
		System.out.println("****************");
		//上面的写法可以简化为下面的：
		employees.stream().map(Employee::getName).filter(name->name.length()>2).forEach(System.out::println);
		System.out.println("****************");
		//case2:
		strings.stream().map(SteamApiTest::fromStringToStream).forEach(s->{
			s.forEach(System.out::println);
		});
		System.out.println("****************");
		//flatMap(Function f)---接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流。实现的功能跟case2是一致的
		strings.stream().flatMap(SteamApiTest::fromStringToStream).forEach(System.out::println);
	}

	/**
	 * 排序
	 */
	@Test
	public void test4(){
		List<Integer> list = Arrays.asList(6, 5, 10, -3, 100, 28);
		//sorted--自然排序
		list.stream().sorted().forEach(System.out::println);
		System.out.println("**************");
		//sorted(Comparator)--定制排序
		List<Employee> employees = getEmployees();
		employees.stream().sorted((emp1,emp2)->{
			int ageCompareResult = Integer.compare(emp1.getAge(),emp2.getAge());
			if (ageCompareResult != 0){
				return ageCompareResult;
			}
			//按照工资自小到大倒序排列
			return -Double.compare(emp1.getSalary(),emp2.getSalary());
		}).forEach(System.out::println);
		System.out.println("**************");
		//上面的写法可以使用链式简化为如下(工资按照自小到大顺序排列的)：
		employees.stream().sorted(Comparator.comparingInt(Employee::getAge).thenComparingDouble(Employee::getSalary)).forEach(System.out::println);
	}

	/**
	 * 匹配与查找
	 * 	allMatch(Predicate p)--检查是否匹配所有元素
	 * 	anyMatch(Predicate p)--检查是否至少匹配一个元素
	 * 	noneMatch(Predicate p)--检查是否没有匹配的元素
	 * 	findFirst---返回第一个元素
	 * 	findAny---返回当前流中的任意元素
	 * 	count---返回流中元素的总个数
	 * 	max(Comparator c)---返回流中最大值
	 * 	min(Comparator c)---返回流中最小值
	 * 	forEach(Consumer c)---内部迭代
	 */
	@Test
	public void test5(){
		List<Employee> employees = getEmployees();
		//是否所有的员工的年龄都大于18
		boolean allMatch = employees.stream().allMatch(employee -> employee.getAge() > 18);
		System.out.println("allMatch:"+allMatch);
		//是否存在员工的工资大于100000
		boolean anyMatch = employees.stream().anyMatch(employee -> employee.getSalary() > 100000);
		System.out.println("anyMatch:"+anyMatch);
		//是否存在不姓雷的员工
		boolean noneMatch = employees.stream().noneMatch(employee -> employee.getName().startsWith("雷"));
		System.out.println("noneMatch:"+noneMatch);
		//查找员工中的第一个
		Optional<Employee> employee1 = employees.stream().findFirst();
		System.out.println("findFirst:"+employee1.get());
		//查找员工中的任何一个
		Optional<Employee> employee2 = employees.stream().findAny();
		System.out.println("findAny:"+employee2.get());
		//计算工资大于5000的员工个数
		long count = employees.stream().filter(employee -> employee.getSalary() > 5000).count();
		System.out.println("count:"+count);
		//获取最高的工资
		Optional<Double> max = employees.stream().map(employee -> employee.getSalary()).max(Double::compare);
		System.out.println("max:"+max.get());
		//获取最低工资的员工
		Optional<Employee> min = employees.stream().min(Comparator.comparingDouble(Employee::getSalary));
		System.out.println("min:"+min.get());
		//流的内部迭代
		employees.stream().forEach(System.out::println);
		//使用集合的遍历操作
		employees.forEach(System.out::println);
	}

	/**
	 * 归约
	 * reduce(T identity, BinaryOperator)——可以将流中元素反复结合起来，得到一个值。返回 T
	 * reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
	 */
	@Test
	public void test6(){
		//计算1-10的自然数之和
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Integer reduceSum = integers.stream().reduce(0, Integer::sum);
		System.out.println("reduceSum:"+reduceSum);
		//计算公司所有员工工资的总和
		List<Employee> employees = getEmployees();
		Optional<Double> salarySum = employees.stream().map(Employee::getSalary).reduce(Double::sum);
		//下面的写法也可以
		Optional<Double> salarySum2 = employees.stream().map(Employee::getSalary).reduce((d1,d2)->d1+d2);
		System.out.println("salarySum:"+salarySum.get());
		System.out.println("salarySum2:"+salarySum2.get());
	}

	/**
	 * 收集
	 * collect(Collector c)---将流转换为其它形式。
	 * 接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
	 */
	@Test
	public void test7(){
		//查找工资大于6000的员工，结果返回为一个List或Set
		List<Employee> employees = getEmployees();
		List<Employee> collectList = employees.stream().filter(employee -> employee.getSalary() > 6000).collect(Collectors.toList());
		collectList.forEach(System.out::println);
		System.out.println("*****************");
		Set<Employee> collectSet = employees.stream().filter(employee -> employee.getSalary() > 6000).collect(Collectors.toSet());
		collectSet.forEach(System.out::println);
	}

	/**
	 * 将字符串中的多个字符,转换成字符集合，然后转换成对应的Stream的实例
	 * @param str
	 * @return
	 */
	public static Stream<Character> fromStringToStream(String str){
		List<Character> list = new ArrayList<>();
		for(Character c : str.toCharArray()){
			list.add(c);
		}
		return list.stream();
	}

	/**
	 * 组装一个对象List集合
	 * @return
	 */
	public List<Employee> getEmployees(){
		List<Employee> data = new ArrayList<>();
		data.add(new Employee(101,"雷布斯",40,20030.00));
		data.add(new Employee(102,"余大嘴",40,30200.00));
		data.add(new Employee(103,"白永祥",39,1220.02));
		data.add(new Employee(104,"卢冰伟",28,20388.08));
		data.add(new Employee(105,"库克",68,120030.99));
		data.add(new Employee(106,"常程",42,6030.66));
		return data;
	}

	/**定义一个内部类*/
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Employee{
		private Integer id;
		private String name;
		private Integer age;
		private double salary;
	}
}
