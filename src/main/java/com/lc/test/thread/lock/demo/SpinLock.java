package com.lc.test.thread.lock.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: 自定义一个多线程的自旋锁
 * 					循环进行比较，没有类似wait的阻塞等待。
 * 				通过CAS操作完成自旋锁，t1线程先进来调用getmylock，然后持有5秒钟；
 * 				t2随后进来发现当前有线程持有锁，不是null，所以，只能通过自旋等待，t1释放之后t2才能获取到。
 * @author: wlc
 * @date: 2019/6/20 0020 0:05
 **/
public class SpinLock {
	public static void main(String[] args) {
		SpinLock myLock = new SpinLock();
		new Thread(()->{
			myLock.getMylock();
			//睡眠5秒，确保t2也去尝试获取锁
			try {
			    TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			myLock.unMylock();
		},"t1").start();

		//睡眠1秒，确保t1先执行。
		try {
		    TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}

		new Thread(()->{
			myLock.getMylock();
			myLock.unMylock();
		},"t2").start();


		//result:
		//t1	 is trying to get mylock...
		//t1	 gets mylock...
		//t2	 is trying to get mylock...
		//t2	 is trying get lock again and again...
		//t2	 is trying get lock again and again...
		//t2	 is trying get lock again and again...
		//t1	 unlocked...
		//t2	 is trying get lock again and again...
		//t2	 gets mylock...
		//t2	 unlocked...
	}

	AtomicReference<Thread> atomicReference = new AtomicReference<>();

	/**
	 * 加锁
	 */
	public void getMylock(){
		Thread thread = Thread.currentThread();
		System.out.println(thread.getName()+"\t is trying to get mylock...");
		//如果该原子引用类中没有其它线程，则该线程获取到锁。注意，while里面不能使用变量代替哦，否则t2是永远获取不到值的。
		while (!atomicReference.compareAndSet(null,thread)){
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println(thread.getName()+"\t is trying get lock again and again...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(thread.getName()+"\t gets mylock...");
	}

	/**
	 * 解锁
	 */
	public void unMylock(){
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread,null);
		System.out.println(thread.getName()+"\t unlocked...");
	}
}


