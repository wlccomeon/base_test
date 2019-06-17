package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 当Queue中的任务数量达到limit值之后，采用何种拒绝策略通知提交者。
 * @author: wlc
 * @date: 2019/6/9 0009 20:56
 **/
@FunctionalInterface
public interface DenyPolicy {

	void reject(Runnable runnable,ThreadPool threadPool);

	/**
	 * 抛弃任务，没有任何反应
	 */
	class DiscardDenyPolicy implements DenyPolicy{
		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			//do nothing
		}
	}

	/**
	 * 向任务提交者抛出异常
	 */
	class AbortDenyPolicy implements DenyPolicy{
		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			throw new RunnableDenyException("the runnable "+runnable+" will be abort");
		}
	}

	/**
	 * 该拒绝策略会使任务在提交者所在的线程中执行任务
	 */
	class RunnerDenyPolicy implements DenyPolicy{
		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			if(!threadPool.isShutdown()){
				runnable.run();
			}
		}
	}
}
