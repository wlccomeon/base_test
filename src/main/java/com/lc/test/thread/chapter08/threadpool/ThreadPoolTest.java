package com.lc.test.thread.chapter08.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 线程池测试类
 * @author: wlc
 * @date: 2019/6/10 0010 0:05
 **/
public class ThreadPoolTest {

	@Test
	public void test() throws InterruptedException{
		//任务队列最多允许1000个任务
		final ThreadPool threadPool = new BasicThreadPool(2,6,4,1000);
		//定义20个任务并提交给线程池
		for (int i =0;i<20;i++){
			threadPool.execute(()->{
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName()+" is running and done.");
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			});
			for (;;){
				//不断输出线程池的信息
				System.out.println("getActiveCount:"+threadPool.getActiveCount());
				System.out.println("getQueueSize:"+threadPool.getQueueSize());
				System.out.println("getCoreSize:"+threadPool.getCoreSize());
				System.out.println("getMaxSize:"+threadPool.getMaxSize());
				System.out.println("=============================================");
				TimeUnit.SECONDS.sleep(5);
			}
		}
	}


}
