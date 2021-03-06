package com.lc.test.thread.safe.test;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: i++问题的探讨
 * 				 i++不是原子性操作，包括获取i值，计算i+1,将i+1赋值给i等3个步骤。
 * 				 volatile保证了内存可见性和顺序性，但无法保证原子性，所以无法保证i++的多线程安全性。
 * 				 synchronized可以；
 * 				 原子类如AtomicInteger也可以，只不过i++的形式有点变动，使用的getandincreasement
 * @author: wlc
 * @date: 2019/6/17 0017 18:48
 **/
@Log
public class IPlusPlus {

	/**
	 * 定义初始变量
	 */
	private int val = 0;

	private AtomicInteger aiVal = new AtomicInteger(0);

	/**
	 * 使用读写锁
	 */
	public static ReentrantLock lock = new ReentrantLock();

	/**
	 * 使用synchronized进行锁定，保证i++安全
	 */
	@Test
	public void safePlus1(){
		for (int i=1; i<=50;i++ ){
			new Thread(()->{
				getNext();
				//为更加清晰显示线程效果，sleep1秒
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"test-thread-"+i).start();
		}
	}
	public synchronized void getNext() {
		val++;
		System.out.println("线程" + Thread.currentThread().getName() + "当前val值为：" + val);
	}

	/**
	 * 使用juc包下的原子类（基于cas）实现i++多线程安全
	 */
	@Test
	public void safePlus2(){
		for (int i=1; i<=50;i++ ){
			new Thread(()->{
				getNext2();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"test-thread-"+i).start();
		}
	}
	public void getNext2() {
		int result = aiVal.getAndIncrement();
		System.out.println("线程" + Thread.currentThread().getName() + "当前result值为：" + result);
	}

	/**
	 * 使用读写锁实现线程安全
	 */
	@Test
	public void safePlus3(){
		for (int i=1; i<=50;i++ ){
			new Thread(()->{
				getNext3();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"test-thread-"+i).start();
		}
	}

	public void getNext3() {
		lock.lock();
		val++;
		System.out.println("线程" + Thread.currentThread().getName() + "当前val值为：" + val);
		lock.unlock();
	}

	/**
	 * 未添加任何锁，直接多线程相加
	 * 可能出现的问题：
	 * 			1.多个线程获取的数值相同或某些数值丢失
	 * 				线程test-thread-34当前val值为：48
	 * 				线程test-thread-31当前val值为：50
	 * 				线程test-thread-32当前val值为：49
	 * 				线程test-thread-33当前val值为：48
	 * 			2.总和不为50
	 * 				线程test-thread-32当前val值为：45
	 * 				线程test-thread-31当前val值为：46
	 * 				线程test-thread-30当前val值为：47
	 * 				线程test-thread-29当前val值为：48
	 * 				线程test-thread-28当前val值为：49
	 */
	@Test
	public void unsafePlus(){
		for (int i=1; i<=50;i++ ){
			new Thread(()->{
				val++;
				System.out.println("线程" + Thread.currentThread().getName() + "当前val值为：" + val);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			},"test-thread-"+i).start();
		}
	}


	//===============================另外一种i++不安全的表现形式============start==========

	/**
	 * volatile不保证原子性
	 */
	@Test
	public void testVolatileAtomic(){
		MemoryData memoryData = new MemoryData();
		for (int i=0; i<20; i++){
		    new Thread(()->{
				for (int j = 0; j <100 ; j++) {
					memoryData.addOne();
				}
		    },String.valueOf(i)).start();
		}

		//确保上面的20个线程执行完毕，2个线程是指main和gc线程
		while (Thread.activeCount()>2){
			//让出main线程的cpu执行权
			Thread.yield();
		}
		//打印最终结果
		System.out.println("the finally result is: "+memoryData.num);

		//结果为：
		//the finally result is: 1995
	}

	class MemoryData{
		/**使用volatile修饰num，测试其不保证原子性的问题*/
		volatile int num = 0;

		/**
		 * 对num加1
		 */
		public void addOne(){
			num++;
		}
	}

	//===============================另外一种i++不安全的表现形式============end============



}
