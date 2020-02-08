package com.lc.test.datastructure.queue.arrayqueue;

/**
 * @author wlc
 * @desc
 * @date 2020-02-07 20:12:51
 */
public class CircleArrayQueueTest {
	static CircleArrayQueue<Integer> circleArrayQueue = new CircleArrayQueue<>(6);
	static{
		//初始化队列数据
		circleArrayQueue.enQueue(1);
		circleArrayQueue.enQueue(6);
		circleArrayQueue.enQueue(5);
		circleArrayQueue.enQueue(2);
		circleArrayQueue.enQueue(3);
	}

	public static void main(String[] args) {

		System.out.println("========原始循环数组队列=============");
		circleArrayQueue.showQueueEle();

		System.out.println("===========循环数组队列满==========");
		testFullQueue();

		System.out.println("==========循环队列出队2个===========");
		circleArrayQueue.deQueue();
		circleArrayQueue.deQueue();
		circleArrayQueue.showQueueEle();

		System.out.println("=========循环队列再入队2个============");
		circleArrayQueue.enQueue(5);
		circleArrayQueue.enQueue(6);
		circleArrayQueue.showQueueEle();

		System.out.println("===========循环队列为空时出队列==========");
		testEmptyQueue();

		//========原始循环数组队列=============
		//arr[0]=1
		//arr[1]=6
		//arr[2]=5
		//arr[3]=2
		//arr[4]=3
		//===========循环数组队列满==========
		//队列已满，无法添加数据
		//==========循环队列出队2个===========
		//arr[2]=5
		//arr[3]=2
		//arr[4]=3
		//=========循环队列再入队2个============
		//arr[2]=5
		//arr[3]=2
		//arr[4]=3
		//arr[5]=5
		//arr[0]=6
		//===========循环队列为空时出队列==========
		//队列为空，无法获取数据
		//
		//Process finished with exit code 0
	}
	public static void testFullQueue(){
		try {
			circleArrayQueue.enQueue(2);
			circleArrayQueue.showQueueEle();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	public static void testEmptyQueue(){
		try {
			circleArrayQueue.deQueue();
			circleArrayQueue.deQueue();
			circleArrayQueue.deQueue();
			circleArrayQueue.deQueue();
			circleArrayQueue.deQueue();
			circleArrayQueue.deQueue();
			circleArrayQueue.showQueueEle();
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
