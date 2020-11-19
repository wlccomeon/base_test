package com.lc.test.java8;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

	private ThreadLocal<Map<String,String>> keyWords = new ThreadLocal<>();

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
		Employee emp1 = new Employee(103, "白永祥", 39, 1220.02,true);
		Employee emp2 = new Employee(104, "卢冰伟", 28, 20388.08,true);
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
		employees.add(new Employee(106,"常程",42,6030.66,true));
		employees.add(new Employee(106,"常程",42,6030.66,true));
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
		employees.stream().forEach(System.out::println);
		List<Employee> newResult = employees.stream().sorted((emp1, emp2) -> {
			//先按照年龄自小到大排序
			int ageCompareResult = Integer.compare(emp1.getAge(), emp2.getAge());
			if (ageCompareResult != 0) {
				return ageCompareResult;
			}
			//按照工资从大到小排列
			return -Double.compare(emp1.getSalary(), emp2.getSalary());
		}).collect(Collectors.toList());
		System.out.println("**************");
		newResult.stream().forEach(System.out::println);
		System.out.println("**************");
		//上面的写法可以使用链式简化为如下(工资按照自大到小顺序排列的)：
		employees.stream().sorted(Comparator.comparingInt(Employee::getAge).thenComparing(Employee::getSalary,Comparator.reverseOrder())).forEach(System.out::println);
	}

	/**
	 * sort和sorted的对比
	 * sort不需要
	 */
	@Test
	public void compareSortAndSorted(){
//		List<Employee> employees = getEmployees();
//		System.out.println("*******未排序之前*****");
//		employees.forEach(System.out::println);
//		//sort排序会改变list元素本身,布尔型排序默认从false到true进行排序
//		employees.sort(Comparator.comparing(Employee::getIsChinesee).reversed().thenComparing(Employee::getAge));
//		System.out.println("********使用布尔型排序后********");
//		employees.forEach(System.out::println);
//		//使用stream.sorted则不会
//		List<Employee> employees1 = getEmployees();
//		employees1.stream().sorted(Comparator.comparing(Employee::getSalary).reversed());
//		System.out.println("******sorted排序******");
//		employees1.forEach(System.out::println);

		System.out.println("*******原始数据********");
		List<Employee> employees2 = getEmployees();
		employees2.stream().forEach(System.out::println);
		System.out.println("*****排序赋新值之后,原集合对象的引用将会更改******");
		employees2 = employees2.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList());
		employees2.stream().forEach(System.out::println);

	}

	/**
	 * 将集合转换为Map
	 */
	@Test
	public void mapTest(){
		List<Employee> list = getEmployees();
		Map<Integer, Employee> collect = list.stream().collect(Collectors.toMap(Employee::getId,
				employee -> employee));
		System.out.println("collect = " + collect);
	}

	@Test
	public void booleanSortTest(){

	}

	/**
	 * 复杂排序：多个条件
	 */
	@Test
	public void testComplexSort(){
		List<Employee> employees = getEmployees();
		String key = "雷军";
		//Comparator.reverseOrder()是对单个条件的翻转，而reversed则是对整个排序结果的翻转
		//下面的写法能够代替下下面的复杂写法
		employees.stream().sorted(Comparator.comparing((Employee employee) -> key.equals(employee.getName()),Comparator.reverseOrder())
				.thenComparing(employee -> employee.getName().contains(key),Comparator.reverseOrder())
				.thenComparing(Employee::getAge))
				.collect(Collectors.toList())
				.forEach(System.out::println);
//		employees.stream().sorted((emp1,emp2)->{
//			//先按照名字排列：名字为雷军的，排第1位，包含雷军的排在后面（如果包含雷军的有多个，则按照年龄排序）
//			if("雷军".equals(emp1.getName()) && "雷军".equals(emp2.getName())){
//				return 0;
//			}
//			if("雷军".equals(emp1.getName()) && !"雷军".equals(emp2.getName())){
//				return -1;
//			}
//			if (!"雷军".equals(emp1.getName()) && "雷军".equals(emp2.getName())){
//				return 1;
//			}
//			if (emp1.getName().contains("雷军") && emp2.getName().contains("雷军")){
//				//先按照年龄自小到大排序
//				int ageCompareResult = Integer.compare(emp1.getAge(),emp2.getAge());
//				if (ageCompareResult != 0){
//					return ageCompareResult;
//				}
//				//按照工资自小到大倒序排列
//				return -Double.compare(emp1.getSalary(),emp2.getSalary());
//			}
//
//			if (emp1.getName().contains("雷军") && !emp2.getName().contains("雷军")){
//				return -1;
//			}
//			return 0;
//		}).forEach(System.out::println);
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
		System.out.println("********");
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
		data.add(new Employee(101,"雷军",40,20030.00,true));
		data.add(new Employee(102,"余雷军",20,30200.00,true));
		//实际是白永祥，这里随便写的
		data.add(new Employee(104,"卢冰伟",40,20388.08,true));
		data.add(new Employee(103,"白雷军",40,1220.02,true));
		data.add(new Employee(105,"库克",68,120030.99,false));
		data.add(new Employee(106,"常程",42,6030.66,true));
		return data;
	}

	@Test
	public void testBoolean(){
		//按照 等于keyword → 包含keyword → 年龄为40 的规则进行排序
		String keyword = "雷军";
		List<Employee> employees = getEmployees();
		List<Employee> collect = employees.stream().sorted(Comparator.comparing((Employee employee) -> employee.getName().equals(keyword),Comparator.reverseOrder())
				.thenComparing(employee -> employee.getName().contains(keyword),Comparator.reverseOrder())
				.thenComparing(employee -> 40==employee.getAge(),Comparator.reverseOrder())).collect(Collectors.toList());
		collect.forEach(System.out::println);
	}

	/**
	 * 将一个list赋值给另外一个list
	 */
	@Test
	public void copyList(){
		List<Employee> originList = getEmployees();
		originList.stream().forEach(employee -> System.out.println(JSON.toJSONString(employee)));
		List<Employee> targetList = new ArrayList<>();
		targetList = originList.stream().map(employee -> {
			Employee employee1 = new Employee();
			try {
//				BeanUtils.copyProperties(employee1, employee);
			} catch (Exception e) {
			}
			return employee1;
		}).collect(Collectors.toList());
		//结果不正确。。。
		targetList.stream().forEach(employee -> System.out.println(JSON.toJSONString(employee)));
	}

	/**
	 * 遍历一个list中的元素并赋值
	 */
	@Test
	public void forEachAndSetList(){
		List<Employee> employees = getEmployees();
		employees.stream().forEach(employee -> System.out.println(JSON.toJSONString(employee)));
		employees.stream().forEach(employee -> {
			employee.setAge(100);
		});
		System.out.println("================================");
		employees.stream().forEach(employee -> System.out.println(JSON.toJSONString(employee)));
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
		private Boolean isChinese;
	}

	@Test
	public void randomList(){
		List<Long> list = new ArrayList<>();
		for (long i = 0; i < 5; i++) {
			list.add(i);
		}
		System.out.println("listToString(getRandomList(list, 2),',') = " + listToString(getRandomList(list, 2), ','));
	}

	@Test
	public void StringArray2ListTest(){
		String[] array = {"2","3"};
		List<String> strings = Arrays.asList(array);
		List<Long> collect = strings.stream().map(Long::valueOf).collect(Collectors.toList());
		System.out.println("collect = " + collect);
		List<Long> collect1 = Arrays.stream(array).map(Long::valueOf).collect(Collectors.toList());
		System.out.println("collect1 = " + collect1);
	}

	/**
	 * @function:从list中随机抽取若干不重复元素
	 * @param paramList:被抽取list
	 * @param count:抽取元素的个数
	 * @return:由抽取元素组成的新list
	 */
	public  List<Long> getRandomList(List<Long> paramList,int count){
		if(count==0){
			return null;
		}
		Random random=new Random();
		//临时存放产生的list索引，去除重复的索引
		List<Integer> tempList=new ArrayList<>();
		//生成新的list集合
		List<Long> newList=new ArrayList();
		int temp=0;
		//如果数据小于1，取一条数据
		if(count<=1){
			temp = random.nextInt(paramList.size());
			newList.add(paramList.get(temp));
		}else {
			for(int i=0;i<Math.ceil(count);i++){
				//初始化一个随机数，将产生的随机数作为被抽list的索引
				temp=random.nextInt(paramList.size());
				//判断随机抽取的随机数
				if(!tempList.contains(temp)){
					tempList.add(temp);
					newList.add(paramList.get(temp));
				}
				else{
					i--;
				}
			}
		}
		return newList;
	}

	/**
	 * list转string
	 * @param list 原始list
	 * @param separator 分隔符
	 * @return
	 */
	public String listToString(List list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (i < list.size() - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 测试在stream的map操作中添加数据
	 * 结果：不论是Array还是List，均为空
	 * 		将map操作改成foreach操作就能得到添加的数据了
	 */
	@Test
	public void testStreamMapOpt(){
		String expiredKey  = "a:2:3";
		String[] splits = expiredKey.split(":");
		String anchorType = splits.length > 1 ? splits[1] : "";
		String anchorId = splits.length > 2 ? splits[2] : "";
		//2:3
		System.out.println(anchorType+":"+anchorId);
		//测试数组在map映射中进行操作的时候，是否能够成功
		Set<String> strSet = new HashSet<>();
//		Arrays.stream(splits).map(split -> strSet.add(split));
		Arrays.stream(splits).forEach(split -> strSet.add(split));
		strSet.forEach(System.out::println);
		//测试list在map映射中进行添加操作
		List<Employee> employees = getEmployees();
		Set<Employee> employeeSet = new HashSet<>();
//		employees.stream().map(employee -> employeeSet.add(employee));
		employees.stream().forEach(employee -> employeeSet.add(employee));
		employeeSet.forEach(System.out::println);
	}
	@Test
	public void longCompareTest(){
		List<Long> longList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			longList.add((long) i);
		}
		System.out.println("========排序之前============");
		longList.forEach(System.out::println);
		List<Long> collect = longList.stream().sorted(Comparator.comparing(Long::longValue,Comparator.reverseOrder())).collect(Collectors.toList());
		System.out.println("=========排序之后============");
		collect.forEach(System.out::println);
	}
}

