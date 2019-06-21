package com.lc.test.thread.lock.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 多个condition的操纵
 *				题目：多线程之间按顺序调用，实现A->B->C,三个线程启动后，要求如下：
 *			  		  A打印5次，B打印10次，C打印15次，
 *			  		  然后执行同样的操作，共10轮。
 *			  	关键点：a/b/c之间有序，通过condition来实现，a通知b，b通知c，c通知d。
 *
 * @author: wlc
 * @date: 2019/6/21 0021 11:34
 **/
public class MultiCondition {

	public static void main(String[] args) {
		ShareResource shareResource = new ShareResource();

		//A打印,切勿写成循环创建10个线程。。。
		new Thread(()->{
			for (int j = 0; j <5 ; j++) {
				shareResource.print5();
			}
		},"A").start();

		//B打印
		new Thread(()->{
			for (int j = 0; j <10 ; j++) {
				shareResource.print10();
			}
		},"B").start();

		//C打印
		new Thread(()->{
			for (int j = 0; j <15 ; j++) {
				shareResource.print15();
			}
		},"C").start();



	}

}

/**
 * 资源类
 */
class ShareResource{

	/**这里使用1代表线程A，2代表线程B，3代表线程C
	 * 通过判断和更改num的值，控制线程的执行。*/
	private int num = 1;
	/**通过condition的等待和唤醒控制线程通信*/
	Lock lock = new ReentrantLock();
	Condition c1 = lock.newCondition();
	Condition c2 = lock.newCondition();
	Condition c3 = lock.newCondition();

	/**
	 * 打印5次
	 */
	public void print5(){
		lock.lock();
		try {
			//判断
			while (num!=1){
				try {
					c1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//干活,打印5次，并修改为B。
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName()+"打印\t"+i);
			}
			num = 2;
			//通知,C2可执行
			c2.signal();
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
		    lock.unlock();
		}

	}

	/**
	 * 打印10次
	 */
	public void print10(){
		lock.lock();
		try {
			//判断
			while (num!=2){
				try {
					c2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//干活,10次，并修改为C。
			for (int i = 1; i <= 10; i++) {
				System.out.println(Thread.currentThread().getName()+"打印\t"+i);
			}
			num = 3;
			//通知，C3可执行
			c3.signal();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	/**
	 * 打印15次
	 */
	public void print15(){
		lock.lock();
		try {
			//判断
			while (num!=3){
				try {
					c3.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//干活,打印15次，并修改为A。
			for (int i = 1; i <= 15; i++) {
				System.out.println(Thread.currentThread().getName()+"打印\t"+i);
			}
			num = 1;
			//通知，c1可执行
			c1.signal();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}
