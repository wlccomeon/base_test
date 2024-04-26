package com.lc.test.thread.lock.demo;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
//		reEntrantLockTest();
		//		synchronizedTest();
		File file = new File("F:\\settings.xml");
		try {
			DataInputStream is = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = is.read(bufferOut))!=-1){

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void fairLockTest(){
		Lock lock = new ReentrantLock(true);
		//结果：
		//当前线程：Thread-1
		//当前线程：Thread-2
		//当前线程：Thread-3
		//当前线程：Thread-1
		//当前线程：Thread-2
		//当前线程：Thread-3
//		Lock lock = new ReentrantLock();
		//结果：
		//当前线程：Thread-1
		//当前线程：Thread-1
		//当前线程：Thread-2
		//当前线程：Thread-2
		//当前线程：Thread-3
		//当前线程：Thread-3
		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				for (int j = 0; j < 2; j++) {
					lock.lock();
					System.out.println("当前线程：" + Thread.currentThread()
							.getName());
					lock.unlock();
				}
			}).start();
		}
		//不要被第一个for循环给迷惑了，因为多线程中，i=0,i=1,i=2执行不一定是按照顺序来的
		//从上述结果可以看出，使用公平锁线程获取锁的顺序是：A -> B -> C -> A -> B -> C，也就是按顺序获取锁。
		// 而非公平锁，获取锁的顺序是 A -> A -> B -> B -> C -> C，原因是所有线程都争抢锁时，因为当前执行线程处于活跃状态，
		// 其他线程属于等待状态（还需要被唤醒），所以当前线程总是会先获取到锁，所以最终获取锁的顺序是：A -> A -> B -> B -> C -> C。
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
