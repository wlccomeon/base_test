package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 线程池接口
 * @author: wlc
 * @date: 2019/6/9 0009 20:43
 **/
public interface ThreadPool {

	/**
	 * 提交任务到线程池
	 * @param runnable
	 */
	void execute(Runnable runnable);

	/**
	 * 关闭线程池
	 */
	void shutdown();

	/**
	 * 获取初始线程数量
	 * @return
	 */
	int getInitSize();

	/**
	 * 获取核心线程数量
	 * @return
	 */
	int getCoreSize();

	/**
	 * 获取线程池中最大线程数量
	 * @return
	 */
	int getMaxSize();

	/**
	 * 获取线程池中用于缓存任务的队列大小
	 * @return
	 */
	int getQueueSize();

	/**
	 * 获取线程池中活跃的线程数量
	 * @return
	 */
	int getActiveCount();

	/**
	 * 查看线程池是否已被关闭
	 * @return
	 */
	boolean isShutdown();
}
