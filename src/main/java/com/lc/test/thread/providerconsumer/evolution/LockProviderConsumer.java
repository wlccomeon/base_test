package com.lc.test.thread.providerconsumer.evolution;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 生产者与消费者，使用不同方式进行演进
 * 				1.synchronized+notify+wait
 * 				2.lock+await+signal
 * 				3.blockingqueue
 * 				本类中采用第二版
 * 	//口诀：
 * 	//1.线程 操作 资源类
 * 	//2.判断 干活  通知
 * 	//3.防止虚假唤醒
 * @author wlc
 * @date: 2019/6/21 0021 0:19
 **/
public class LockProviderConsumer {

	public static void main(String[] args) {

		ShareResourceForLock shareResourceForLock = new ShareResourceForLock();

		//消费线程
		for (int i=1; i<= 2; i++){
		    new Thread(()->{
		        shareResourceForLock.decrement();
		    },"consumer"+String.valueOf(i)).start();
		}

		//生产线程
		for (int i=1; i<= 2; i++){
		    new Thread(()->{
		        shareResourceForLock.increment();
		    },"provider"+String.valueOf(i)).start();
		}

		//result:
		//consumer1 is waiting for provider...
		//consumer2 is waiting for provider...
		//provider1	1
		//provider2 is waiting for consumer...
		//consumer1	0
		//consumer2 is waiting for provider...
		//provider2	1
		//consumer2	0

	}


}

/**
 * 资源类
 */
class ShareResourceForLock{
	/**操作资源*/
	private int num = 0;
	/**使用可重入锁*/
	private Lock lock = new ReentrantLock();
	/**设置条件*/
	private Condition condition = lock.newCondition();
	/**
	 * 生产方法
	 */
	public void increment(){
		lock.lock();
		try {
			//判断,num不为0，则需要等待消费者。
			while (num!=0){
				System.out.println(Thread.currentThread().getName()+" is waiting for consumer...");
				condition.await();
			}
			//干活
			num++;
			System.out.println(Thread.currentThread().getName()+"\t"+num);
			//通知，随意一个消费者
			condition.signalAll();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	/**
	 * 消费方法
	 */
	public void decrement(){
		lock.lock();
		try {
			//判断,num为0，则需要等待生产
			while (num==0){
				System.out.println(Thread.currentThread().getName()+" is waiting for provider...");
				condition.await();
			}
			//干活
			num--;
			System.out.println(Thread.currentThread().getName()+"\t"+num);
			//通知
			condition.signalAll();
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
		    lock.unlock();
		}
	}


}