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

		//jdk1.8 result：
		//[GC (Allocation Failure) [PSYoungGen: 6590K->808K(9216K)] 6590K->4912K(19456K), 0.0041797 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		//Heap
		// PSYoungGen      total 9216K, used 7189K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
		//  eden space 8192K, 77% used [0x00000000ff600000,0x00000000ffc3b798,0x00000000ffe00000)
		//  from space 1024K, 78% used [0x00000000ffe00000,0x00000000ffeca020,0x00000000fff00000)
		//  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
		// ParOldGen       total 10240K, used 4104K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
		//  object space 10240K, 40% used [0x00000000fec00000,0x00000000ff002020,0x00000000ff600000)
		// Metaspace       used 3500K, capacity 4498K, committed 4864K, reserved 1056768K
		//  class space    used 387K, capacity 390K, committed 512K, reserved 1048576K
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
