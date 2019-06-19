package com.lc.test.thread.cas.aba;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description: CAS的ABA问题解决方案
 * 				 使用juc中提供的AtomicStampedReference，可以通过版本号的形式来解决ABA的问题
 * @author: wlc
 * @date: 2019/6/19 0019 16:33
 **/
public class ResolveAba {

	/**初始化值大小为1，版本号为1*/
	AtomicStampedReference<Integer> initData = new AtomicStampedReference<>(100,1);
	/**使用volatile和加锁的形式，保证线程t1和t2的执行顺序*/
	volatile boolean flag = false;

	public static void main(String[] args) {

		ResolveAba resolveAba = new ResolveAba();
//		resolveAba.testResolveABA3();
		resolveAba.testResolveABA2();
	}

	/**
	 * 在junit的方法中执行线程测试，
	 * sleep后面的代码都无法执行到。。不知何种原因。放到main方法中就可以了。
	 */
	@Test
	public void testResolveABA1(){
		//使用t1进行ABA式的修改
		new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//睡眠1秒，确保t2也拿到了初始化的版本
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//第1次更改，将100改为101
			initData.compareAndSet(100,101,initData.getStamp(),initData.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+" the first update value is "+initData.getReference()+" version is "+ initData.getStamp());
			//第2次更改，将101改回100
			initData.compareAndSet(101,100,initData.getStamp(),initData.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+" the second update value is "+initData.getReference()+" version is "+ initData.getStamp());

		},"t1").start();

		//使用t2修改ABA式的数据
		new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//睡眠3秒，确保t1执行完毕两次更改
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean result = initData.compareAndSet(100,2019,initVersion,initVersion+1);
			System.out.println(Thread.currentThread().getName()+" update result is "+result+" current value is "+initData.getReference()+" version is "+initData.getStamp());
		},"t2").start();
	}


	/**
	 * 使用线程通信机制和加锁的方式实现t1和t2按照规定执行顺序
	 */
	public void testResolveABA2(){

		//使用t1进行ABA式的修改
		Thread t1 = new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//判断下面的代码是否可以执行
			if (flag){
				synchronized (initData){
					//第1次更改，将100改为101
					initData.compareAndSet(100,101,initData.getStamp(),initData.getStamp()+1);
					System.out.println(Thread.currentThread().getName()+" the first update value is "+initData.getReference()+" ,version is "+ initData.getStamp());
					//第2次更改，将101改回100
					initData.compareAndSet(101,100,initData.getStamp(),initData.getStamp()+1);
					System.out.println(Thread.currentThread().getName()+" the second update value is "+initData.getReference()+" ,version is "+ initData.getStamp());
					//通知t2,执行剩余代码
					initData.notify();
				}
			}
		},"t1");

		//使用t2修改ABA式的数据
		Thread t2 = new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//当t2拿到初始版本号之后，更改flag为true，
			flag = true;
			synchronized (initData){
				try {
					//避免t2线程中try...catch外的代码执行。将执行权交给t1.
					initData.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean result = initData.compareAndSet(100,2019,initVersion,initVersion+1);
				System.out.println(Thread.currentThread().getName()+" update result is "+result+" ,current value is "+initData.getReference()+" version is "+initData.getStamp());
			}
		},"t2");

		t1.start();
		t2.start();
	}


	@Test
	public void testPrint(){
		new Thread(()->{
			System.out.println("start");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end");
		},"t").start();

		//结果：只有start打印出了。
	}

	/**
	 * 在普通方法中使用main方法调用
	 */
	public void testResolveABA3(){
		//使用t1进行ABA式的修改
		new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//睡眠1秒，确保t2也拿到了初始化的版本
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//第1次更改，将100改为101
			initData.compareAndSet(100,101,initData.getStamp(),initData.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+" the first update value is "+initData.getReference()+" version is "+ initData.getStamp());
			//第2次更改，将101改回100
			initData.compareAndSet(101,100,initData.getStamp(),initData.getStamp()+1);
			System.out.println(Thread.currentThread().getName()+" the second update value is "+initData.getReference()+" version is "+ initData.getStamp());

		},"t1").start();

		//使用t2修改ABA式的数据
		new Thread(()->{
			int initVersion = initData.getStamp();
			System.out.println(Thread.currentThread().getName()+" init version "+initVersion);
			//睡眠3秒，确保t1执行完毕两次更改
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean result = initData.compareAndSet(100,2019,initVersion,initVersion+1);
			System.out.println(Thread.currentThread().getName()+" update result is "+result+" current value is "+initData.getReference()+" version is "+initData.getStamp());
		},"t2").start();
	}

}
