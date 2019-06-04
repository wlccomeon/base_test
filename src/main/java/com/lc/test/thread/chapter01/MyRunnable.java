package com.lc.test.thread.chapter01;

/**
 * 通过重写runnable的run方法运行线程
 * 模拟窗口叫号功能
 */
public class MyRunnable implements Runnable {

	private int index = 1;
	/**最多叫号到50*/
	private static final int MAX_NUM = 500;

	@Override
	public void run() {
		while (index<=MAX_NUM){
			System.out.println(Thread.currentThread()+" 的号码是："+(index++));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
