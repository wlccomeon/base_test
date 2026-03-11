package com.lc.test.baseknowlege.enums;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author wlc
 * @desc
 * @date 2020-04-02 15:07:24
 */
public class EnumTest {
	public static void main(String[] args) {
//		Assert.notNull(null,"this object is null");
//		System.out.println(StatusEnum.STARTED.name());
//		String a = "{1,2,3}";
//		List<String> list = Arrays.asList(a.split(","));
//		System.out.println("StatusEnum.valueOf(\"aaa\") = " + StatusEnum.valueOf("aaa"));
		StatusEnum waitting = StatusEnum.valueOf("waitting".toUpperCase());
		System.out.println("waitting = " + waitting);
	}

	@Test
	public void testPrint(){
		System.out.println("plan.A.name() = " + plan.A.name().equals("A"));
		System.out.println("planMethod.A.name() = " + planMethod.A.name().equals("A"));
	}

	enum plan{
		A,B;
	}
	enum planMethod{
		A(),
		B();
	}

}
