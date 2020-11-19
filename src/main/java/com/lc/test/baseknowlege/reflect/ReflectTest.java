package com.lc.test.baseknowlege.reflect;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wlc
 * @desc
 * @date 2019-12-20 12:27:26
 */
public class ReflectTest {

	public static void main(String[] args){
		try {
			String filePath = "C:\\dev_workspace\\idea\\base_test\\src\\main\\resources\\application.yml";
			Properties properties = loadFile2Properties(filePath);
			if (properties!=null){
				User user = new User();
				properties2Object(properties,user);
				System.out.println("user = " + user);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public void test() throws Exception{
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
	}

	/**
	 * 读取文件并加入配置到Properties中
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Properties loadFile2Properties(String filePath) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		Properties properties = new Properties();
		properties.load(in);
		return properties;
	}

	/**
	 * 将Properties中的配置读取出来并为指定的Object中对应的属性进行赋值
	 * @param p
	 * @param object
	 */
	public static void properties2Object(final Properties p, final Object object) {
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			String mn = method.getName();
			if (mn.startsWith("set")) {
				try {
					String tmp = mn.substring(4);
					String first = mn.substring(3, 4);

					String key = first.toLowerCase() + tmp;
					String property = p.getProperty(key);
					if (property != null) {
						Class<?>[] pt = method.getParameterTypes();
						if (pt != null && pt.length > 0) {
							String cn = pt[0].getSimpleName();
							Object arg = null;
							//如有需要，可添加其它类型的数据
							if (cn.equals("int") || cn.equals("Integer")) {
								arg = Integer.parseInt(property);
							} else if (cn.equals("long") || cn.equals("Long")) {
								arg = Long.parseLong(property);
							} else if (cn.equals("double") || cn.equals("Double")) {
								arg = Double.parseDouble(property);
							} else if (cn.equals("boolean") || cn.equals("Boolean")) {
								arg = Boolean.parseBoolean(property);
							} else if (cn.equals("float") || cn.equals("Float")) {
								arg = Float.parseFloat(property);
							} else if (cn.equals("String")) {
								arg = property;
							} else {
								continue;
							}
							method.invoke(object, arg);
						}
					}
				} catch (Throwable ignored) {
				}
			}
		}
	}
}
