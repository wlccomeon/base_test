package com.lc.test.datastructure.queue.arrayqueue;

import com.lc.test.designpattern.singleton.T;

/**
 * @desc 环形数组队列(相对于普通数组队列)
 * 		关键点：
 * 			1.head指向队列的第一个元素(包含该元素)位置，初始值为0；
 * 			2.tail指向队列的最后一个元素(不包含该元素)的后一个位置，初始值为0。空出一个空间作为约定，算法实现起来会比较好理解；
 * 			3.队列满时，条件变为：(tail+1)%capacity==head
 * 			4.队列为空：tail==head
 * 			5.队列中的有效数据的个数为：(tail+capacity-head)%capacity
 * 		    6.入队后新队尾位置为： (tail+1)%capacity，出队后新的队头位置为： (head+1)%capacity
 * @author wlc
 * @date 2020-02-07 13:44:28
 */
public class CircleArrayQueue<T> {
	/**队头，默认为0,指向队列的第一个元素(包含该元素)位置，控制出队列*/
	private int head;
	/**队尾，默认为0,指向队列的最后一个元素(不包含该元素)的后一个位置,控制入队列*/
	private int tail;
	/**队列容量*/
	private int capacity;
	/**对象数组，作为队列元素的容器*/
	private T[] arr;

	/**
	 * 空构造方法，默认设置队列大小为6.
	 */
	public CircleArrayQueue() {
		this(6);
	}

	/**
	 * 带容量的队列构造方法
	 * @param capacity
	 */
	public CircleArrayQueue(int capacity) {
		this.capacity = capacity;
		arr =(T[])new Object[capacity];
		//int默认为0，下面两句可以不写
		head = 0;
		tail = 0;
	}

	/**
	 * 判断队列是否已满
	 * @return
	 */
	public boolean isFull(){
		return (tail+1)%capacity==head;
	}

	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return head == tail;
	}

	/**
	 * 入队列
	 */
	public void enQueue(T element){
		if (isFull()){
			throw new RuntimeException("队列已满，无法添加数据");
		}
		//在原来的队尾赋值，并确定新的队尾位置(因为是个环形，不能直接++，否则可能会数组越界)
		arr[tail]=element;
		tail=(tail+1)%capacity;
	}

	public static void main(String[] args) {
		int a = 4;
		int b = 6;
		System.out.println((a+b)%b);
		System.out.println(b/a);
		System.out.println(Math.floorMod(b,a));
	}

	/**
	 * 出队列
	 * @return
	 */
	public T deQueue(){
		if (isEmpty()){
			throw new RuntimeException("队列为空，无法获取数据");
		}
		//获取原位置元素，将原位置置空，产生新的队头位置
		T element = arr[head];
		arr[head] = null;
		head = (head+1)%capacity;
		return element;
	}

	/**
	 * 遍历并打印队列中的元素,为空的元素不打印
	 */
	public void showQueueEle() {
		if (isEmpty()){
			System.out.println("队列为空");
			return;
		}
		int size = getQueueSize();
		//从head的位置开始遍历，注意超过size之后数组的下标
		for (int i=head;i<size+head;i++){
			System.out.println("arr["+i%capacity+"]="+arr[i%capacity]);
		}
	}

	/**
	 * 获取环形队列中的数据长度
	 * @return
	 */
	public int getQueueSize(){
		int size = tail-head;
		if(size<0){
			return capacity+size;
		}
		return size;
	}
}
