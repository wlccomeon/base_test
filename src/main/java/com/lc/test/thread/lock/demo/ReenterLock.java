package com.lc.test.thread.lock.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 可重入锁(也叫递归锁）：
 * 				 指的是：同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 				 		在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。
 * 				 也就是说：线程可以进入任何一个它已经拥有的锁锁同步着的代码块。
 *
 * 				ReentrantLock和Synchronized都是可重入锁
 * 				可防止死锁
 * @author: wlc
 * @date: 2019/6/19 0019 23:32
 **/
public class ReenterLock {

	public static void main(String[] args) {
		reEntrantLockTest();
		//		synchronizedTest();
	}

	public static void reEntrantLockTest(){
		MyHome myHome = new MyHome();
		new Thread(()->{
			myHome.get();
		},"t3").start();

		new Thread(()->{
			myHome.get();
		},"t4").start();

		//result:
		//t3	 comes in the get method..
		//t3	 comes in the set method..
		//t4	 comes in the get method..
		//t4	 comes in the set method..
	}

	/**
	 * 测试synchronized可重入锁
	 */
	public static void synchronizedTest() {
		MyHome myHome = new MyHome();
		new Thread(()->{
			myHome.enterGate();
		},"t1").start();

		new Thread(()->{
			myHome.enterGate();
		},"t2").start();

		//result:
		//t1	 opens the gate...
		//t1	 comes in the washroom..
		//t2	 opens the gate...
		//t2	 comes in the washroom..
	}

}
class MyHome{

	//可重入锁synchronized的测试==========================start
	public synchronized void enterGate(){
		System.out.println(Thread.currentThread().getName()+"\t opens the gate...");
		//测试在打开大门之后，厕所的门是否还在上着锁。
		enterWashroom();
	}

	public synchronized void enterWashroom(){
		System.out.println(Thread.currentThread().getName()+"\t comes in the washroom..");
	}
	//可重入锁synchronized的测试==========================end

	Lock lock = new ReentrantLock();
	public void get(){
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t comes in the get method..");
			set();
		}catch (Exception e){

		}finally {
			//lock与unlock必须成对出现，否则可能会造成死锁？？。
			lock.unlock();
		}
	}

	public void set(){
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName()+"\t comes in the set method..");
		}catch (Exception e){

		}finally {
			lock.unlock();
		}
	}

}
