package com.lc.test.thread.providerconsumer.evolution;

/**
 * @Description: 生产者与消费者，使用不同方式进行演进
 * 				1.synchronized+notify+wait
 * 				2.lock+await+signal
 * 				3.blockingqueue
 * 				本类中采用第一版
 * 	//口诀：
 * 	//1.线程 操作 资源类
 * 	//2.判断 干活  通知
 * 	//3.防止虚假唤醒
 * @author: wlc
 * @date: 2019/6/20 0020 23:40
 **/
public class SyncProviderConsumer {

	public static void main(String[] args) {
		ShareResourceForSync shareResource = new ShareResourceForSync();
		for (int i=1; i<=5 ; i++){
		    new Thread(()->{
				shareResource.decrement();
			},"consumer"+String.valueOf(i)).start();
		}

		for (int i=1; i<= 5; i++){
		    new Thread(()->{
				shareResource.increment();
		    },"provider"+String.valueOf(i)).start();
		}

		//result:
		//consumer1 is waiting for provider...
		//provider1	1
		//consumer2	0
		//consumer1 is waiting for provider...
		//provider2	1
		//consumer1	0
	}


}

/**
 * 资源类
 */
class ShareResourceForSync{
	/**资源属性*/
	private int num = 0;
	/**被锁的对象*/
	private Object MUTEXT = new Object();
	/**
	 * 生产方法
	 */
	public void increment(){
		synchronized (MUTEXT){
			//判断，如果不为0，则说明已经生产过，等待消费。
			while (num!=0){
				try {
					System.out.println(Thread.currentThread().getName()+" is waiting for consumer..");
					MUTEXT.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//干活，+1
			num++;
			System.out.println(Thread.currentThread().getName()+"\t"+num);
			//通知，任何一个消费者即可。
			MUTEXT.notify();
		}

	}

	/**
	 * 消费方法
	 */
	public void decrement(){
		synchronized (MUTEXT){
			//判断,如果为0，则表示还没有生产。
			while (num==0){
				try {
					System.out.println(Thread.currentThread().getName()+" is waiting for provider...");
					MUTEXT.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//干活
			num--;
			System.out.println(Thread.currentThread().getName()+"\t"+num);
			//通知
			MUTEXT.notify();
		}
	}
}