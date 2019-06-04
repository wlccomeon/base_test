package com.lc.test.thread.chapter01;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * 测试类
 */
public class MainTest {

	/**
	 * 模拟窗口叫号功能(有线程安全问题)
	 */
	@Test
	public void ticketWindow(){

		final MyRunnable myRunnable = new MyRunnable();

		Thread t1 = new Thread(myRunnable,"1号窗口");
		Thread t2 = new Thread(myRunnable,"2号窗口");
		Thread t3 = new Thread(myRunnable,"3号窗口");
		Thread t4 = new Thread(myRunnable,"4号窗口");

		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

	/**
	 * 使用λ表达式创建线程
	 */
	@Test
	public void lambdCreateThread(){
		IntStream.range(0,5).mapToObj(MainTest::createThread).forEach(Thread::start);
	}

	public static Thread createThread(final int name){
		return new Thread(() -> System.out.println(Thread.currentThread().getName()),"lc-"+name);
	}


}
