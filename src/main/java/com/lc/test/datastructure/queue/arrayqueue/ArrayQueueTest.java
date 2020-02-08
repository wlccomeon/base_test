package com.lc.test.datastructure.queue.arrayqueue;

/**
 * @author wlc
 * @desc
 * @date 2020-02-07 11:40:38
 */
public class ArrayQueueTest {
	static ArrayQueue<Integer> arrayQueue = new ArrayQueue(3);
	static{
		//初始化队列数据
		arrayQueue.enQueue(1);
		arrayQueue.enQueue(6);
		arrayQueue.enQueue(5);
	}

	public static void main(String[] args) {
		arrayQueue.showQueueEle();
		System.out.println("=====================");
		testFullQueue();

		System.out.println("=====================");
		arrayQueue.deQueue();
		arrayQueue.deQueue();
		arrayQueue.showQueueEle();

		System.out.println("=====================");
		testEmptyQueue();
		//运行结果：
		//arr[0]=1
		//arr[1]=6
		//arr[2]=5
		//=====================
		//队列已满，无法添加数据
		//=====================
		//arr[2]=5
		//=====================
		//队列为空，无法获取数据
	}
	public static void testFullQueue(){
		try {
			arrayQueue.enQueue(2);
			arrayQueue.showQueueEle();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void testEmptyQueue(){
		try {
			arrayQueue.deQueue();
			arrayQueue.deQueue();
			arrayQueue.showQueueEle();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
