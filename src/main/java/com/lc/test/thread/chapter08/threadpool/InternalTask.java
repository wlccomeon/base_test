package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 该类是runnable的一个实现类，主要用于线程池内部。
 * 				 通过从RunnableQueue中不断的从queue中获取某个runnable，运行run方法。
 * @author: wlc
 * @date: 2019/6/9 0009 21:06
 **/
public class InternalTask implements Runnable{

	private final RunnableQueue runnableQueue;

	private volatile boolean running  = true;

	public InternalTask(RunnableQueue runnableQueue){
		this.runnableQueue=runnableQueue;
	}

	@Override
	public void run() {
		//如果当前任务为running并且没被中断，则其将不断的从queue中获取runnable，执行run方法
		while(running && !Thread.currentThread().isInterrupted()){
			try {
				Runnable task = runnableQueue.take();
				task.run();
			}catch (InterruptedException e){
				running = false;
				break;
			}
		}
	}

	/**
	 * 停止当前任务，主要在线程池的shutdown方法中使用
	 */
	public void stop(){
		this.running = false;
	}
}
