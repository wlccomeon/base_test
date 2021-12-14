package com.lc.test.baseknowlege.enums;

import org.apache.commons.lang3.StringUtils;
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
}
