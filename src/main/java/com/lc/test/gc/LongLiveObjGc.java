package com.lc.test.gc;

/**
 * 长期存活的对象将进入老年代：年龄默认为15岁。
 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * 		   -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * @author wlc
 */
public class LongLiveObjGc {

	private static final int _1MB = 1024*1024;

	public static void main(String[] args) {
		byte[] allocation1,allocation2,allocation3,allocation4;
		allocation1 = new byte[_1MB/2];
		allocation2 = new byte[_1MB/2];
		allocation3 = new byte[4*_1MB];

	}

}
