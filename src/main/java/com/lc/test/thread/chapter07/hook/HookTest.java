package com.lc.test.thread.chapter07.hook;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 钩子线程的操作
 * @author: wlc
 * @date: 2019/6/9 0009 18:50
 **/
public class HookTest {


	public static void main(String[] args) throws InterruptedException {

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Thread.yield();
			System.out.println(Thread.currentThread().getName()+" hook线程启动.. ");
			System.out.println(" 线程即将停止... ");
			System.out.println(" 执行资源释放等操作完毕... ");
		}));

		for (int i=0;i<10;i++){
			TimeUnit.SECONDS.sleep(1);
			System.out.println("执行第 "+ i +" 秒");
		}

		System.out.println(" 主线程执行完毕.. ");
	}


}
