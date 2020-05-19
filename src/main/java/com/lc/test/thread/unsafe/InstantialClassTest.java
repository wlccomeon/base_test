package com.lc.test.thread.unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.io.*;

/**
 * @desc 实例化类的几种方式：
 * （1）通过构造方法实例化一个类；
 * （2）通过Class实例化一个类；
 * （3）通过反射实例化一个类；
 * （4）通过克隆实例化一个类；
 * （5）通过反序列化实例化一个类；
 * （6）通过Unsafe实例化一个类；
 * @author wlc
 * @date 2020-02-24 17:11:41
 */
public class InstantialClassTest {
	private static Unsafe unsafe;
	static {
		unsafe = UnsafeTest.getUnsafe();
	}

	public static void main(String[] args) throws Exception {
		//1.构造方法，直接new
		User user1 = new User();
		//2.通过class，其实原理就是反射
		User user2 = User.class.newInstance();
		//3.通过反射
		User user3 = User.class.getConstructor().newInstance();
		//4.克隆
		User user4 = (User) user1.clone();
		//5.反序列化
		User user5 = unSerialize(user1);
		//6.unsafe，Unsafe.allocateInstance()只会给对象分配内存，并不会调用构造方法，所以这里只会返回int类型的默认值0
		User user6 = (User)unsafe.allocateInstance(User.class);

		System.out.println(user1.getAge());
		System.out.println(user2.getAge());
		System.out.println(user3.getAge());
		System.out.println(user4.getAge());
		System.out.println(user5.getAge());
		System.out.println(user6.getAge());

	}

	/**
	 * 反序列化User实例
	 * @return
	 */
	private static User unSerialize(User user1) throws  Exception{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D://object.txt"));
		oos.writeObject(user1);
		oos.close();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D://object.txt"));
		//反序列化
		User user5 = (User)ois.readObject();
		ois.close();
		return user5;
	}


	@Data
	private static class User implements Serializable,Cloneable {
		int age;
		public User(){
			this.age = 10;
		}
		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}

}
