package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 任务队列，用于缓存提交到线程池中的任务
 * @author: wlc
 * @date: 2019/6/9 0009 20:49
 **/
public interface RunnableQueue {

	/**
	 * 当有新的任务进来时，首先会使用该方法添加到队列中
	 * @param runnable
	 */
	void offer(Runnable runnable);

	/**
	 * 工作线程通过该方法获取runnable
	 * @return
	 */
	Runnable take() throws InterruptedException;

	/**
	 * 获取队列中任务的数量
	 * @return
	 */
	int size();
}
