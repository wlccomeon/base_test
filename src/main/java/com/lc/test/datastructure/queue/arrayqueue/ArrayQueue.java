package com.lc.test.datastructure.queue.arrayqueue;

/**
 * @desc 模拟数组实现的队列,注意：
 * 			队头、队尾 位置
 * 			队空、队满 判断
 * 		目前存在的问题：出队列的数组部分空出来了，无法再次使用。
 * @author wlc
 * @date 2020-02-06 22:08:06
 */
public class ArrayQueue<T>{

	/**队头，默认为-1,指向队列头的前一个位置(不包含数据)，控制出队列*/
	private int head;
	/**队尾，默认为-1,指向队列尾的位置(包含数据),控制入队列*/
	private int tail;
	/**队列容量*/
	private int capacity;
	/**对象数组，作为队列元素的容器*/
	private T[] arr;

	/**
	 * 空构造方法，默认设置队列大小为6.
	 */
	public ArrayQueue() {
		this(6);
	}

	/**
	 * 带容量的队列构造方法
	 * @param capacity
	 */
	public ArrayQueue(int capacity) {
		this.capacity = capacity;
		arr =(T[])new Object[capacity];
		head = -1;
		tail = -1;
	}

	/**
	 * 判断队列是否已满,只看队尾是否为数组的最大下标即可
	 * @return
	 */
	public boolean isFull(){
		return tail == capacity-1;
	}

	/**
	 * 判断队列是否为空，只看队尾与队头的位置是否相同即可
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
		//队尾+1，并在该位置赋值
		tail++;
		arr[tail]=element;
	}

	/**
	 * 出队列
	 * @return
	 */
	public T deQueue(){
		if (isEmpty()){
			throw new RuntimeException("队列为空，无法获取数据");
		}
		//队头+1，并在该位置获取值，然后返回,最后置空该位置的值
		head++;
		T element = arr[head];
		arr[head] = null;
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
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]==null){
				continue;
			}
			System.out.println("arr["+i+"]="+arr[i].toString());
		}
	}
}
