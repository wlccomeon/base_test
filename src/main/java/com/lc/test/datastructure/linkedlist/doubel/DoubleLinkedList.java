package com.lc.test.datastructure.linkedlist.doubel;

import com.lc.test.datastructure.linkedlist.Hero;

/**
 * @desc 构造一个具有头结点的双向链表
 * @author wlc
 * @date 2020-02-09 21:11:52
 */
public class DoubleLinkedList<E> {
	/**定义头结点*/
	Node<E> head = new Node<>(null);

	public void add(E elment){
		Node<E> temp = head;
		
	}

	public void delete(E element){

	}

	public void modify(E element){

	}

	public void printList(){

	}





	class Node<E>{
		/**数据域*/
		E data;
		/**前置指针域*/
		Node pre;
		/**后置指针域*/
		Node next;
		public Node(E data) {
			this.data = data;
		}
	}
}
