package com.lc.test.thread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @desc 直接操作cpu的unfafe类，这个默认是不能被应用直接操作的。不过可以通过反射的方式获取到该类。
 * @author wlc
 * @date 2020-02-24 16:34:09
 */
public class UnsafeTest {

	public static void main(String[] args) throws InterruptedException {
//		Unsafe unsafe = getUnsafe();
		Counter counter = new Counter();
		//起100个线程，每个线程自增10000次
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		IntStream.range(0,100).forEach(i->threadPool.submit(
				()->IntStream.range(0,10000).forEach(j->counter.increment())));
		threadPool.shutdown();
		TimeUnit.MILLISECONDS.sleep(2000);
		//打印1000000
		System.out.println(counter.getCount());
	}

	/**
	 * 获取Unsafe类实例
	 * @return
	 */
	public static Unsafe getUnsafe(){
		Unsafe unsafe=null;
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			unsafe= (Unsafe)f.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return unsafe;
	}


}
/**
 * 线程安全的计数器(基于cas操作)
 */
class Counter{
	private volatile int count = 0;
	private static long offset;
	private static  Unsafe unsafe;
	static {
		unsafe = UnsafeTest.getUnsafe();
		try {
			offset = unsafe.objectFieldOffset(Counter.class.getDeclaredField("count"));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	public void increment(){
		int before = count;
		//失败了就重试，直到成功
		while (!unsafe.compareAndSwapInt(this,offset,before,before+1)){
			before = count;
		}
	}
	public int getCount(){
		return count;
	}
}
