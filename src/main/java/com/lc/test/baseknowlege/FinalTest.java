package com.lc.test.baseknowlege;

import com.lc.test.entity.User;

/**
 * @desc final修饰的方法参数
 * @author wlc
 * @date 2020-01-08 22:04:09
 */
public class FinalTest {

	public static void main(String[] args) {
		FinalTest finalTest = new FinalTest();
		System.out.println(finalTest.getInt(1));
		System.out.println(finalTest.getUser(new User()));
		System.out.println(finalTest.printFinally());
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

	/**
	 * 对于final修饰的引用类型，不能改变其引用对象实例，但可以为对象赋值
	 * @param user
	 * @return
	 */
	private User getUser(final User user){
		//不能修改对象引用，编译不通过
//		user = new User();
		user.setId(1);
		user.setName("kc");
		return user;
	}

	private Integer printFinally(){
		int a = 1;
		try {
			a = 2;
			System.out.println("catch:"+a);
			return a;
		}catch (Exception e){
			a = 3;
			System.out.println();
			return a;
		}finally {
			a = 4;
			System.out.println("finally:"+a);
		}
	}


}
