package com.lc.test.thread.lock.demo.countdownlatch;

import java.util.concurrent.*;

/**
 * @Description: 使用countdownlatch加枚举，模拟秦灭六国，一统华夏。
 * 					countdownlatch用来确保除main之外所有的线程都执行完。
 * @author: wlc
 * @date: 2019/6/20 0020 17:41
 **/
public class CountDownLatchTest {
	static final ThreadPoolExecutor threadPool;
	static{
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("my processors-->>"+processors);
		threadPool = new ThreadPoolExecutor(processors, processors, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "my_countdownlatch_thread");
			}
		});
	}

	public static void main(String[] args) throws Exception {

		while (true){
			try {
				CountDownLatch cdl = new CountDownLatch(2);
				int a = 1;
				String b = "2";
				//任务2
				threadPool.execute(new MyRunnable(cdl,"任务2"));
				//任务3
				threadPool.execute(new MyRunnable(cdl,"任务3"));
				threadPool.execute(new Thread(() -> System.out.println("线程名："+Thread.currentThread().getName()+Thread.currentThread().getId())));
				cdl.await();
				//任务1
				System.out.println("任务1-->>"+b);
				break;
			}catch (Exception e){
				e.printStackTrace();
				break;
			}
		}
		threadPool.shutdown();
	}

	public static void printQinDynasty(){
		/**使用countdownlatch，设置一个初始值*/
		CountDownLatch cdl = new CountDownLatch(6);

		//启动6个线程，模拟六国被灭
		for (int i=1; i<= 6; i++){
			new Thread(()->{
				try {
					//run方法中的执行代码块放到try.catch中
					System.out.println(Thread.currentThread().getName()+"\t国被灭");
				}catch (Exception e){
					System.out.println("Thread:"+Thread.currentThread().getName()+" 发生错误:"+e.getMessage());
				}finally {
					//确保线程发生意外时能够countdown，否则main方法中的await将一直不能中断，无法执行后续操作
					cdl.countDown();
				}
			},CountriesEnum.getValue(i)).start();
		}

		//如果cdl计数器没有归零，则继续等待
		try {
			//可以设置等待过期时间，不设置则一直等待。
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("秦灭六国，华夏大一统实现...");

		//result:
		//魏	国被灭
		//齐	国被灭
		//燕	国被灭
		//楚	国被灭
		//韩	国被灭
		//赵	国被灭
		//秦灭六国，华夏大一统实现...
	}

}
