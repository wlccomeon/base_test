package com.lc.test.baseknowlege;

import com.lc.test.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc final修饰的方法参数
 * @author wlc
 * @date 2020-01-08 22:04:09
 */
public class FinalTest {

	public final String finalStr = "final_String";
	private final User user = new User();

	public static void main(String[] args) {
		FinalTest finalTest = new FinalTest();
		System.out.println(finalTest.getInt(1));
		System.out.println(finalTest.getUser(new User()));
		System.out.println(finalTest.printBasicTypeFinally());
		System.out.println(finalTest.printMapFinally());
		finalTest.testNewFinalInnerClass();
	}

	/**
	 * 对于final修饰的基本类型，只能使用，不能修改值
	 * @param i
	 * @return
	 */
	private Integer getInt(final int i){
		//下面的写法编译不通过，不能再次赋值了
//		i = 0;
		return i;
	}

	private User getFinalUser(){
		user.setId(1);
		return user;
	}

	/**
	 * 对于final修饰的引用类型，不能改变其引用对象实例，但可以为对象赋值
	 * @param user
	 * @return
	 */
	private User getUser(final User user){
		//不能修改对象引用，编译不通过
//		user = new User();
//		user = null;
		user.setId(1);
		user.setName("kc");
		return user;
	}

	/**
	 * 1.对于基本类型中finally重新赋值，方法返回值不受影响
	 * 2.如果在finally中有return语句，try中的return将会失效
	 * //结果：try:2
	 * 		  finally:4
	 *        方法返回值：2
	 * @return
	 */
	private Integer printBasicTypeFinally(){
		int a = 1;
		try {
			a = 2;
			System.out.println("try:"+a);
			return a;
		}catch (Exception e){
			a = 3;
			System.out.println();
			return a;
		}finally {
			a = 4;
			System.out.println("finally:"+a);
//			return a; 若在这里return了，则该方法最终返回的是4;否则为2。
		}
	}

	/**
	 * 1.对于如容器之类的引用类型，finally中重新赋值，会影响方法的最终返回值
	 * 2.如果在finally中有return语句，try中的return将会失效
	 * 3.若try中产生了Exception，那catch代码块中在return之前也会执行finally中的代码，并最终影响方法的返回值
	 * //结果：try_map:{key1=try_value}
	 * 		  finally_map:{key1=finally_value}
	 *        方法返回值：{key1=finally_value}
	 * @return
	 */
	private Map printMapFinally(){
		Map<String, Object> data = new HashMap<>();
		try {
			data.put("key1","try_value");
			System.out.println("try_map:"+data.toString());
			int a = 1/0;
			return data;
		}catch (Exception e){
			System.out.println("出错啦："+e.getMessage());
			data.put("key1","catch_value");
			return data;
		}finally {
			data.put("key1","finally_value");
			System.out.println("finally_map:"+data.toString());
		}
	}

	final class TestFinalInnerClass{
		private Map<String,Object> map = new HashMap<>();
	}
	private void testNewFinalInnerClass(){
		TestFinalInnerClass finalInnerClass = new TestFinalInnerClass();
		finalInnerClass.map.put("key","val");
		System.out.println(finalInnerClass.map);
	}


}
