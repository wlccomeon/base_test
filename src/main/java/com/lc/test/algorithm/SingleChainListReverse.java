package com.lc.test.algorithm;

import java.io.Serializable;

/**
 * @desc 单链表反转，例如原链表1→2→3→4→5，反转为5→4→3→2→1
 * 			可使用for循环或者递归
 * @author wlc
 * @date 2020-01-13 18:43:46
 */
public class SingleChainListReverse implements Serializable {

	private static final long serialVersionUID = -2523929290751873767L;

	public static void main(String[] args) {

		Node head = new Node(0);
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		head.setNext(node1);
		node1.setNext(node2);
		node2.setNext(node3);

		// 打印反转前的链表
		Node h = head;
		printNode(h);
		// 调用反转方法
		head = Reverse1(head);

		System.out.println("**************************");
		// 打印反转后的结果
		printNode(head);
	}

	public static void printNode(Node node){
		while (null != node) {
			System.out.print(node.getData() + " ");
			node = node.getNext();
		}
	}

	/**
	 * 递归，在反转当前节点之前先反转后续节点
	 */
	public static Node Reverse1(Node head) {
		// head看作是前一结点，head.getNext()是当前结点，reHead是反转后新链表的头结点
		if (head == null || head.getNext() == null) {
			return head;// 若为空链或者当前结点在尾结点，则直接还回
		}
		Node reHead = Reverse1(head.getNext());// 先反转后续节点head.getNext()
//		head.getNext().setNext(head);// 将当前结点的指针域指向前一结点
//		head.setNext(null);// 前一结点的指针域令为null;
		Node beforHead=head.getNext();
		head.setNext(null);// 前一结点的指针域令为null;
		beforHead.setNext(head);
		return reHead;// 反转后新链表的头结点
	}
}

class Node {
	private int Data;// 数据域
	private Node Next;// 指针域

	public Node(int Data) {
		this.Data = Data;
	}

	public int getData() {
		return Data;
	}

	public void setData(int Data) {
		this.Data = Data;
	}
	public Node getNext() {
		return Next;
	}

	public void setNext(Node Next) {
		this.Next = Next;
	}
}
