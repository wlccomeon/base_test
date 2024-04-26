package com.lc.test.baseknowlege;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @desc uuid生成
 * @author wlc
 * @date 2019-09-19 14:39:37
 */
public class UuidTest {

	public static void main(String[] args) {
		String originUuid = UUID.randomUUID().toString();
		String replaceUuid = originUuid.replace("-","");
		System.out.println("originUuid-->>"+originUuid);
		System.out.println("replaceUuid-->>"+replaceUuid);
		Double a = 2.025;

	}

	@Test
	public void UserTest(){
		User user = new User();
		Field[] declaredFields = user.getClass().getDeclaredFields();
		System.out.println("declaredFields.length = " + declaredFields.length);
		User user1 = new User();
		user.setA(0);
		user.setB("aaa");
		Field[] declaredFields1 = user1.getClass().getDeclaredFields();
		System.out.println("declaredFields1.length = " + declaredFields1.length);
	}

	@Data
	private class  User{
		private Integer a;
		private String b;
	}

	@Test
	public void tryCatchTest(){
		try {
//			int a = 1/0;
		}catch (Exception e){
			try {
				int b = 1/0;
			}catch (Exception ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
