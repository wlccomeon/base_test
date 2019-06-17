package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 创建线程的工厂
 * @author: wlc
 * @date: 2019/6/9 0009 20:53
 **/
public interface ThreadFactory {

	/**
	 * 创建线程方法
	 * @param runnable
	 * @return
	 */
	Thread createThread(Runnable runnable);

}
