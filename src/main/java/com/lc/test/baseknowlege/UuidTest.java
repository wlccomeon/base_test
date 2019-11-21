package com.lc.test.baseknowlege;

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


}
