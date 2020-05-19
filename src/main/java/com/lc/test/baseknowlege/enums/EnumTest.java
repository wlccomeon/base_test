package com.lc.test.baseknowlege.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author wlc
 * @desc
 * @date 2020-04-02 15:07:24
 */
public class EnumTest {
	public static void main(String[] args) {
		System.out.println(StatusEnum.STARTED.name());
		String a = "{1,2,3}";
		List<String> list = Arrays.asList(a.split(","));
	}
}
