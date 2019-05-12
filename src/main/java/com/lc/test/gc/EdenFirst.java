package com.lc.test.gc;

/**
 * 对象优先在Eden区分配，当Eden区装不下时，将引发minorGC。
 * 如果to survivor区装在minorGC的时候装不下对象，则会将其放置Old区
 * vm参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * 堆大小限制为20M
 * JDK1.8和1.7默认使用的PS+ParOld收集器组合
 * @author wlc
 */
public class EdenFirst {
	private static final int _1M = 1024*1024;

	public static void main(String[] args) {
		testAllocation();
	}

	/**
	 * 测试分配
	 */
	public static void testAllocation(){
		byte[] allocation1,allocation2,allocation3,allocation4;

		allocation1 = new byte[2*_1M];
		allocation2 = new byte[2*_1M];
		allocation3 = new byte[2*_1M];
		//存放下面的变量会引发一次minorGC
		allocation4 = new byte[4*_1M];

	}
}
