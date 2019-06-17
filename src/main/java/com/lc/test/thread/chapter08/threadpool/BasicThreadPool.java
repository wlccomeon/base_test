package com.lc.test.thread.chapter08.threadpool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 初始化线程池
 * @author: wlc
 * @date: 2019/6/9 0009 23:19
 **/
public class BasicThreadPool extends Thread implements ThreadPool {

	/**
	 * 初始化线程数量
	 */
	private final int initSize;
	/**
	 * 线程池中最大的线程数量
	 */
	private final int maxSize;
	/**
	 * 线程池中核心线程数量
	 */
	private final int coreSize;
	/**
	 * 线程池中当前活跃的线程数量
	 */
	private int activeCount;

	/**
	 * 创建线程所需的工厂
	 */
	private final ThreadFactory threadFactory;
	/**
	 * 任务队列
	 */
	private final RunnableQueue runnableQueue;
	/**
	 * 线程池是否已被shutdown标识
	 */
	private volatile boolean isShutdown = false;
	/**
	 * 工作线程队列
	 */
	private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

	private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

	private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

	private final long keepAliveTime;

	private final TimeUnit timeUnit;

	public  BasicThreadPool(int initSize,int maxSize,int coreSize,int queueSize){
		this(initSize,maxSize,coreSize,DEFAULT_THREAD_FACTORY,queueSize,DEFAULT_DENY_POLICY,10,TimeUnit.SECONDS);
	}

	public BasicThreadPool(int initSize,int maxSize,int coreSize,ThreadFactory threadFactory,
						   int queueSize,DenyPolicy denyPolicy,long keepAliveTime,TimeUnit timeUnit){
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.coreSize = coreSize;
		this.threadFactory = threadFactory;
		this.runnableQueue = new LinkedRunnableQueue(queueSize,denyPolicy,this);
		this.keepAliveTime = keepAliveTime;
		this.timeUnit = timeUnit;
		this.init();
	}

	/**
	 * 初始化时，先创建initSize个线程
	 */
	private void init(){
		start();
		for (int i = 0; i < initSize; i++){
			newThread();
		}
	}

	/**
	 * 线程池自动维护，创建线程方法
	 */
	private void newThread() {
		//创建线程任务，并且启动
		InternalTask internalTask = new InternalTask(runnableQueue);
		Thread thread = this.threadFactory.createThread(internalTask);
		ThreadTask threadtask = new ThreadTask(thread,internalTask);
		threadQueue.offer(threadtask);
		this.activeCount++;
		thread.start();
	}

	/**
	 * 移除线程方法
	 */
	private void removeThread(){
		//从线程池中移除某个线程
		ThreadTask threadTask = threadQueue.remove();
		threadTask.internalTask.stop();
		this.activeCount--;

	}

	@Override
	public void execute(Runnable runnable) {
		if(this.isShutdown){
			throw new IllegalStateException("the threadpoll is destroyed...");
		}
		//提交任务只是简单的往队列中插入一条记录
		this.runnableQueue.offer(runnable);
	}

	@Override
	public void run() {
		//run方法继承自Thread，主要用于维护线程数量，比如扩容、回收等工作。
		while (!isShutdown && !isInterrupted()){
			try {
				timeUnit.sleep(keepAliveTime);
			}catch (InterruptedException e){
				isShutdown = true;
				break;
			}
			synchronized (this){
				if (isShutdown){
					break;
				}
				//扩充至核心线程数量大小
				if (runnableQueue.size()>0 && activeCount<coreSize){
					for (int i=initSize;i<coreSize;i++){
						newThread();
					}
					//continue的目的在于不想让线程的扩容直接到达maxSize
					continue;
				}
				//当前队列中有未处理的任务，且活跃线程数量小于最大线程数量，则扩容至最大。
				if(runnableQueue.size()>0 && activeCount<maxSize){
					for (int i = coreSize;i<maxSize;i++){
						newThread();
					}
				}
				//如果队列中没有任务，则需要收回，回收至coreSize即可
				if(runnableQueue.size()==0 && activeCount>coreSize){
					for (int i = coreSize;i<activeCount;i++){
						removeThread();
					}
				}
			}
		}
	}

	/**
	 * ThreadTask只是Thread和InternalTask的一个组合
	 */
	private static class ThreadTask{
		public ThreadTask(Thread thread,InternalTask internalTask){
			this.thread = thread;
			this.internalTask = internalTask;
		}
		Thread thread;
		InternalTask internalTask;
	}

	private static class DefaultThreadFactory implements ThreadFactory{
		private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
		private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"+GROUP_COUNTER.incrementAndGet());
		private static final AtomicInteger COUNTER = new AtomicInteger(0);

		@Override
		public Thread createThread(Runnable runnable) {
			return new Thread(group,runnable,"thread-pool-"+COUNTER.getAndIncrement());
		}
	}

	/**
	 * 主要是为了停止BasicThreadPool线程，停止线程池中的活动线程并且将isShutDown开关变量设置为ture
	 */
	@Override
	public void shutdown() {
		synchronized (this){
			if (isShutdown){
				return;
			}
			isShutdown = true;
			threadQueue.forEach(threadTask -> {
				threadTask.internalTask.stop();
				threadTask.thread.interrupt();
			});
		}
	}

	@Override
	public int getInitSize() {
		if(isShutdown){
			throw new IllegalStateException("the threadpool is destroyed...");
		}
		return this.initSize;
	}

	@Override
	public int getCoreSize() {
		if(isShutdown){
			throw new IllegalStateException("the threadpool is destroyed...");
		}
		return this.coreSize;
	}

	@Override
	public int getMaxSize() {
		if(isShutdown){
			throw new IllegalStateException("the threadpool is destroyed...");
		}
		return this.maxSize;
	}

	@Override
	public int getQueueSize() {
		if(isShutdown){
			throw new IllegalStateException("the threadpool is destroyed...");
		}
		return runnableQueue.size();
	}

	@Override
	public int getActiveCount() {
		synchronized (this){
			return this.activeCount;
		}
	}

	@Override
	public boolean isShutdown() {
		return this.isShutdown;
	}


}
