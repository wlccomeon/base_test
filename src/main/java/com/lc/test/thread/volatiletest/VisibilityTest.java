package com.lc.test.thread.volatiletest;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 可见性测试
 * @author: wlc
 * @date: 2019/6/18 0018 18:49
 **/
public class VisibilityTest {

	public static void main(String[] args) {

		MemoryData memoryData = new MemoryData();

		//定义线程1，用来刷新主内存数值
		Thread t1 = new Thread(()->{
			System.out.println("thread "+Thread.currentThread().getName()+" is coming in... value is: "+memoryData.i);
			//睡眠2秒，确保线程t2读取到的是MemoryData中i的初始值
			try {
			    TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			memoryData.flush(20);
			System.out.println("thread "+Thread.currentThread().getName()+" is coming out... value is: "+memoryData.i);
		},"t1");

		//定义线程t2，用来接收t1刷新主内存的信息
		Thread t2 = new Thread(()->{
			while (memoryData.i==0){
				//为0，则什么都不做。
			}
			//不为0，则打印
			System.out.println("thread "+Thread.currentThread().getName()+" receives new value: "+memoryData.i);
		},"t2");

		t1.start();
		t2.start();


	}


}

/**
 * 主内存数据
 */
class MemoryData{

	/**使用volatile实现线程可见性*/
	volatile int i = 0;

	/**
	 * 提供刷新数据的方法
	 */
	public void flush(int newData){
		i = newData;
	}
	//使用volatile运行结果：
	//thread t1 is coming in... value is: 0
	//thread t1 is coming out... value is: 20
	//thread t2 receives new value: 20

	//不使用volatile运行结果：
	//thread t1 is coming in... value is: 0
	//thread t1 is coming out... value is: 20
	//且线程2一直在运行，不会结束。
}
