package com.lc.test.thread.chapter01;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试两次启动同一个线程：
 * 线程一旦启动之后，不论是线程执行完毕还是在执行中，都不能再次被启动。
 * @author wlc
 */
public class StartThreadTwoTimesTest {

	/**
	 * 连续两次启动同一个线程
	 * 		这个方法跟terminateToStart方法虽然报的同一个错误，但过程是不一样的。
	 * 		terminateToStart方法中的线程已经terminate了，无法再次启动。
	 */
	@Test
	public void startTwoTimes(){
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
		thread.start();

		//result:
		//java.java.lang.IllegalThreadStateException
		//	at java.java.lang.Thread.start(Thread.java:708)
	}

	/**
	 * 线程执行完毕之后再启动
	 */
	@Test
	public void terminateToStart() throws InterruptedException {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		thread.start();
		//睡眠2秒，确保上线的线程已经执行完毕
		TimeUnit.SECONDS.sleep(2);
		//企图再次激活thread
		thread.start();

		//result:
		//java.java.lang.IllegalThreadStateException
		//	at java.java.lang.Thread.start(Thread.java:708)
	}
}
