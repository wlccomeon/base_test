package com.lc.test.thread.lock.demo.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wlc
 * @desc
 * @date 2020-03-06 17:38:35
 */
public class MyRunnable implements Runnable {

	private CountDownLatch cdl;
	private String taskName;

	public MyRunnable(CountDownLatch cdl,String taskName) {
		this.cdl = cdl;
		this.taskName=taskName;
	}

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(10);
			System.out.println(Thread.currentThread().getName()+"-"+Thread.currentThread().getId()+"执行"+taskName);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			cdl.countDown();
		}
	}
}
