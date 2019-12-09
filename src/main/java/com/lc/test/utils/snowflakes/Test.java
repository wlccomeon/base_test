package com.lc.test.utils.snowflakes;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wlc
 * @desc
 * @date 2019-12-09 21:00:23
 */
public class Test {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Set<Long> ids = new HashSet<>();
		for (int i = 0; i <30000 ; i++) {
			long id = UniqueID.generateId();
			ids.add(id);
		}
		long end = System.currentTimeMillis();
		System.out.println("雪花算法生成"+ids.size()+"个ID所耗时间:"+(end-start)+"ms");
		System.out.println("雪花算法生成"+ids.size()+"个ID所耗时间:"+(end-start)+"ms");

	}

}
