package com.lc.test.anotation.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 解析user表操作数据库
 * @author wlc
 */
public class UserUtil {
	public static void main(String[] args) {
		User user = new User();
		user.setId(10);
		user.setName("lc");
		user.setEmail("wlc@123.com");
		query(user);
	}

	private static void query(User user) {
		//1.获取类加载器
		Class c = user.getClass();
		//2.获取类上的注解
		boolean isExist = c.isAnnotationPresent(Table.class);
		StringBuffer sb = new StringBuffer();
		if (isExist) {
			Table table = (Table) c.getAnnotation(Table.class);
			String tableName = table.value();
			sb.append("select * from " + tableName + " where 1=1");
		}
		//3.获取所有字段
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			boolean fExist = f.isAnnotationPresent(Column.class);
			if (fExist) {
				String tableName = f.getAnnotation(Column.class).value();
				//4获取字段值
				//4.1反射获取字段方法
				String fieldname = f.getName();
				String methodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				Object fieldValue = null;
				try {
					Method method = c.getMethod(methodName);
					//4.2通过反射方法获取字段值
					fieldValue = method.invoke(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//4.3拼装sql
				if (fieldValue == null || fieldValue instanceof Integer && (Integer) fieldValue == 0) {
					continue;

				}
				if (fieldValue instanceof Integer) {
					sb.append(" and " + tableName + " = " + fieldValue);
				} else if (fieldValue instanceof String) {
					if (((String) fieldValue).contains(",")) {
						String[] values = ((String) fieldValue).split(",");
						sb.append(" " + tableName + " in (");
						for (String v : values) {
							sb.append("'" + v + "',");
						}
						sb.deleteCharAt(sb.length() - 1);
						sb.append(")");
					} else {
						sb.append(" and " + tableName + " = '" + fieldValue + "'");
					}
				}

				System.out.println(sb.toString());
			}
		}
	}
}
