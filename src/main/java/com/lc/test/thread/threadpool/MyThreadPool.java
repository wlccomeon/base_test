package com.lc.test.thread.threadpool;
import java.util.concurrent.*;

/**
 * @Description: 自己手写一个线程池
 * @author: wlc
 * @date: 2019/6/23 0023 15:39
 **/
public class MyThreadPool {

	public static void main(String[] args) {
//		jdkThreadPoolTest();

		ExecutorService myThreadPool = null;

//		使用默认的拒绝策略,超过(队列容量+maximumPoolSize)的直接抛出拒绝的异常。
//		myThreadPool = new ThreadPoolExecutor(2,3,100,
//				TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
		//结果：
		//线程pool-1-thread-1 处理任务0
		//线程pool-1-thread-1 处理任务2
		//线程pool-1-thread-1 处理任务3
		//线程pool-1-thread-1 处理任务4
		//java.util.concurrent.RejectedExecutionException:
		//线程pool-1-thread-2 处理任务1
		//线程pool-1-thread-3 处理任务5

		System.out.println("=======================================");

		//使用CallerRunsPolicy拒绝策略，它不会抛弃任务，也不会抛出异常。如果线程池中来不及执行所有的任务（如maximum+队列容量小于待处理线程数）
		// ，那么多余的会返回给调用方所在的线程（例如main线程）
//		myThreadPool = new ThreadPoolExecutor(2,3,100,
//				TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());
		//结果：
		//线程main 处理任务6
		//线程main 处理任务7
		//线程main 处理任务8
		//线程main 处理任务9
		//线程pool-1-thread-1 处理任务0
		//线程pool-1-thread-1 处理任务2
		//线程pool-1-thread-1 处理任务3
		//线程pool-1-thread-2 处理任务1
		//线程pool-1-thread-1 处理任务4
		//线程pool-1-thread-3 处理任务5

		System.out.println("=======================================");
		//抛弃任务中等待最久的任务，然后把当前任务加入到队列中尝试再次提交当前任务
//		myThreadPool = new ThreadPoolExecutor(2,3,100,
//				TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());
		//结果：
		//线程pool-1-thread-1 处理任务0
		//线程pool-1-thread-1 处理任务7
		//线程pool-1-thread-1 处理任务8
		//线程pool-1-thread-1 处理任务9
		//线程pool-1-thread-3 处理任务5
		//线程pool-1-thread-2 处理任务1

		System.out.println("=======================================");
		//直接丢弃任务策略，不处理也不报任何异常，如果允许任务丢失，这个是最好的策略。
		myThreadPool = new ThreadPoolExecutor(2,3,100,
				TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
		//结果：
		//线程pool-1-thread-1 处理任务0
		//线程pool-1-thread-1 处理任务2
		//线程pool-1-thread-1 处理任务3
		//线程pool-1-thread-1 处理任务4
		//线程pool-1-thread-2 处理任务1
		//线程pool-1-thread-3 处理任务5

		try {
			//模拟10个线程请求
			for (int i = 0; i < 10; i++) {
				final int taskNum = i;
				myThreadPool.execute(() -> {
					System.out.println("线程" + Thread.currentThread().getName() + " 处理任务" + taskNum);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myThreadPool.shutdown();
		}



	}

	/**
	 * 使用jdk提供的几种创建线程池的方式
	 * 缺点：fixedThreadPool和singleThreadPool的队列长度均为Integer.MAX_VALUE,相当于无界队列了，可能会在队列中堆积大量请求，从而导致OOM。
	 * 		cachedThreadPool和ScheduledThreadPool允许创建的线程数量俊文Integer.MAX_VALUE,可能会创建大量的线程，从而导致OOM。
	 * 问题解决：自己手动创建threadpoolexecutor
	 */
	public static void jdkThreadPoolTest() {
		//使用jdk提供的线程池

		//创建一个指定数量线程的线程池，可控制线程最大并发数，超过线程数量的任务，将会在队列中等待。
		// 此策略适合执行长期的任务，性能会好很多。队列类型为LinkedBlockingQueue。
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
		//创建一个可缓存线程池，如果线程池线程数量超过任务需要，将回收闲置线程；否则，创建更多线程。线程的数量为N。
		//适用于时间短且异步的任务。队列类型为SynchronousQueue。
		ExecutorService cachedThreadPool= Executors.newCachedThreadPool();
		//创建一个单线程的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行
		//适用于需要按照顺序执行任务的场景。队列类型为LinkedBlockingQueue。
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

		try {
			for (int i = 0; i < 10; i++) {
				final int num = i;
				fixedThreadPool.execute(()->{
					System.out.println("newFixedThreadPool线程"+Thread.currentThread().getName()+" 处理任务"+num);
				});
			}
			//结果：
			//newFixedThreadPool线程pool-1-thread-1 处理任务0
			//newFixedThreadPool线程pool-1-thread-1 处理任务2
			//newFixedThreadPool线程pool-1-thread-1 处理任务3
			//newFixedThreadPool线程pool-1-thread-1 处理任务4
			//newFixedThreadPool线程pool-1-thread-1 处理任务5
			//newFixedThreadPool线程pool-1-thread-1 处理任务6
			//newFixedThreadPool线程pool-1-thread-1 处理任务7
			//newFixedThreadPool线程pool-1-thread-1 处理任务8
			//newFixedThreadPool线程pool-1-thread-1 处理任务9
			//newFixedThreadPool线程pool-1-thread-2 处理任务1
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			fixedThreadPool.shutdown();
		}

		try {
		    TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("==============================================================");

		try {
			for (int i = 0; i < 10; i++) {
				final int num = i;
				cachedThreadPool.execute(()->{
					System.out.println("newCachedThreadPool线程"+Thread.currentThread().getName()+" 处理任务"+num);
				});
				//模拟任务访问间隔比较长的情况，会创建多少个线程
				//结果：只有1个。如果将该睡眠代码放到Runnable中，那么会创建10个线程。
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
			cachedThreadPool.shutdown();
		}

		try {
		    TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("==============================================================");

		try {
			for (int i = 0; i < 10; i++) {
				final int num = i;
				singleThreadExecutor.execute(()->{
					System.out.println("newSingleThreadExecutor线程"+Thread.currentThread().getName()+" 处理任务"+num);
				});
			}
			//结果：
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务0
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务1
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务2
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务3
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务4
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务5
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务6
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务7
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务8
			//newSingleThreadExecutor线程pool-3-thread-1 处理任务9
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			singleThreadExecutor.shutdown();
		}
	}

	public static Integer getCpucoreNum(){
		Integer num = Runtime.getRuntime().availableProcessors();
		System.out.println("cpu核数为："+num);
		return num;
	}




}
