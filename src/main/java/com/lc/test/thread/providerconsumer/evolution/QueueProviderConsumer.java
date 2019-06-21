package com.lc.test.thread.providerconsumer.evolution;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 生产者与消费者，使用不同方式进行演进
 * 				1.synchronized+notify+wait
 * 				2.lock+await+signal
 * 				3.blockingqueue
 * 	本类中采用第三版，不使用线程通知机制。通过blockingqueue解耦生产者与消费者。
 * 	//口诀：
 * 	//1.线程 操作 资源类
 * 	//2.判断 干活  通知
 * 	//3.防止虚假唤醒
 * @author: wlc
 * @date: 2019/6/21 0021 17:33
 **/
public class QueueProviderConsumer {

	public static void main(String[] args) {

		//使用BlockingQueue的实现类ArrayBlockingQueue，并设置队列容量为10
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
		ShareResourceForQueue queueResource = new ShareResourceForQueue(blockingQueue);

		//启动生产者
		for (int i = 1; i <= 5; i++) {
			new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"\t starts produce...");
				queueResource.produce();
			},"Producer"+String.valueOf(i)).start();
		}

		//启动消费者
		for (int i = 1; i <= 5; i++) {
			new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"\t starts consume...");
				queueResource.consume();
			},"Consumer"+String.valueOf(i)).start();
		}


		//沉睡5秒之后，停止生产和消费
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//分割线
		System.out.println("========================================");
		System.out.println("========================================");
		System.out.println("5 seconds later , stop produce or consume");
		queueResource.stop();

		//result:
		//Producer	 starts produce...
		//Consumer	 starts consume...
		//========================================
		//========================================
		//Producer	 produce msg 1 succeed...
		//Consumer	 consume msg 1 succeed...
		//Producer	 produce msg 2 succeed...
		//Consumer	 consume msg 2 succeed...
		//Producer	 produce msg 3 succeed...
		//Consumer	 consume msg 3 succeed...
		//Producer	 produce msg 4 succeed...
		//Consumer	 consume msg 4 succeed...
		//Producer	 produce msg 5 succeed...
		//Consumer	 consume msg 5 succeed...
		//========================================
		//========================================
		//5 seconds later , stop produce or consume
		//flag is false , stop produce...
		//Consumer	 consume msg null failed for a long time...stop consume
	}

}

/**
 * 资源类
 */
class ShareResourceForQueue{

	/**定义一个消费和生产队列的总开关，默认开启,由于没有使用lock，这里需要使用volatile*/
	private volatile boolean FLAG = true;

	/**定义父接口blockingqueue，兼容客户端传的7种queue*/
	private BlockingQueue<String> blockingQueue = null;
	ShareResourceForQueue(BlockingQueue<String> blockingQueue){
		this.blockingQueue = blockingQueue;
		//设计的时候使用接口接收，那么每次请求应该知道具体的是哪种blockingqueue，打印即可。
		System.out.println(blockingQueue.getClass().getName());
	}

	/**定义原子类型，用来生产消息*/
	private AtomicInteger msg = new AtomicInteger(1);

	/**
	 * 队列消息生产方法
	 */
	public void produce(){
		//确保生产者全部启动
		try {
		    TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		//定义获取消息变量
		String msgData = null;
		//定义消息添加到队列中的结果
		boolean result;
		//总开关开启，则持续生产
		while (FLAG){
			try {
				msgData = String.valueOf(msg.getAndIncrement());
				//使用offer方法，不会直接抛出错误，且会返回添加结果
				result = blockingQueue.offer(msgData,2L,TimeUnit.SECONDS);
				if (result){
					System.out.println(Thread.currentThread().getName()+"\t produce msg "+msgData+" succeed...");
				}else{
					System.out.println(Thread.currentThread().getName()+"\t produce msg "+msgData+" failed...");
				}
				//设置每隔1秒生产一个消息，以便观察
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("flag is "+FLAG+" ,"+Thread.currentThread().getName()+" stop produce...");
	}

	/**
	 * 队列消息消费方法
	 */
	public void consume(){
		//确保消费者全部启动
		try {
		    TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		//定义获取队列中的消息
		String result = null;
		//总开关开启，则持续消费
		while (FLAG){
			//使用offer方法，不会直接抛出错误，且会返回添加结果
			try {
				result = blockingQueue.poll(2L,TimeUnit.SECONDS);
				if (StringUtils.isNotBlank(result)){
					System.out.println(Thread.currentThread().getName()+"\t consume msg "+result+" succeed...");
				}else{
					System.out.println(Thread.currentThread().getName()+"\t consume msg "+result+" failed for a long time...stop consume");
					//超时获取不到消息之后，将Flag设置为false，return结束掉该次循环
					FLAG = false;
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * 关闭生产和消费开关的方法
	 */
	public void stop(){
		this.FLAG = false;
	}


}



