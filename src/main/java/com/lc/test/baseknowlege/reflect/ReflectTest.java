package com.lc.test.baseknowlege.reflect;

import com.lc.test.entity.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wlc
 * @desc
 * @date 2019-12-20 12:27:26
 */
public class ReflectTest {
	private static final Map<String,String> finalMap = new ConcurrentHashMap<>(2);

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		//通过类加载器获取对象
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = classLoader.loadClass("com.lc.test.entity.User");

		//构造对象实例
		Constructor<?> constructor = clazz.getDeclaredConstructor((Class[]) null);
		User user = (User)constructor.newInstance();

		//获取方法，并set值
		Method setNameMethod = clazz.getMethod("setName", String.class);
		setNameMethod.invoke(user,"lc-setname-by-reflect");
		System.out.println("user-->>"+user.toString());
		System.out.println("setName-->>"+user.getName());

		finalMap.put("user1","val1");
		finalMap.put("user2","val2");
		finalMap.put("user3","val3");
		for (String key: finalMap.keySet()) {
			System.out.println(key+"-->>"+finalMap.get(key));
		}
	}
}
