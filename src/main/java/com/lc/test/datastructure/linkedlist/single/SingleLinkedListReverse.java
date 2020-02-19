package com.lc.test.datastructure.linkedlist.single;

import java.util.Stack;

/**
 * @author wlc
 * @desc
 * @date 2020-02-09 12:23:44
 */
public class SingleLinkedListReverse {

	public static void main(String[] args) {
		//构造链表
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		node3.next = node4;
		node2.next = node3;
		node1.next = node2;
		System.out.println("========原始链表数据=========");
		printNode(node1);

		//翻转
		Node recursionNode = reverseByRecursion(node1);
		System.out.println("=========递归翻转之后链表数据========");
		printNode(recursionNode);

//		System.out.println("=========循环再次翻转之后链表数据========");
//		Node loopNode = reverseByLoop2(recursionNode);
//		printNode(loopNode);
//
//		System.out.println("======使用栈的方式再次翻转数据==========");
//		reverseByStackAndPrint(loopNode);

		//结果：
		//========原始链表数据=========
		//1->2->3->4
		//=========递归翻转之后链表数据========
		//4->3->2->1
		//=========循环再次翻转之后链表数据========
		//1->2->3->4
		//======使用栈的方式再次翻转数据==========
		//4->3->2->1

	}


	/**
	 * 递归翻转(不用借助变量，直接压栈)
	 * newHead 接收的是上一个循环中的对象的引用，head会在每次递归的时候产生两个两个节点的链表
	 * 	缺点：改变了原来的链表
	 * @param head
	 * @return
	 */
	static Node reverseByRecursion(Node head){
		if(head == null || head.next == null){
			return head;
		}
		Node newHead = reverseByRecursion(head.next);
		System.out.println("****************");
		printNode(newHead);
		printNode(head);
		head.next.next = head;
		head.next = null;
		printNode(newHead);
		printNode(head);
		return newHead;
	}

	/**
	 * 循环翻转：缺点；改变了原来的链表
	 * 由于单链表没有指向前一个结点的指针，
	 * 所以我们定义一个指向前一个结点的指针pre，用于存储每一个节点的前一个结点。
	 * 接下来还需要定义一个保存当前结点的指针cur，以及下一个节点的next。
	 * 定义好之后，遍历单链表，将当前结点的指针指向前一个结点，之后定义三个指针向后移动，直至遍历到最后一个结点为止。
	 *
	 * @param head
	 * @return
	 */
	static Node reverseByLoop(Node head) {
		//单链表为空或只有一个节点，直接返回原单链表，不用翻转了
		if (head == null || head.next == null){
			return head;
		}
		//前一个节点指针
		Node preNode = null;
		//当前节点指针
		Node curNode = head;
		//下一个节点指针
		Node nextNode;

		while (curNode != null){
			//nextNode 指向下一个节点
			nextNode = curNode.next;
			//将当前节点next域指向前一个节点
			curNode.next=preNode;
			//preNode 指针向后移动
			preNode = curNode;
			//curNode指针向后移动
			curNode = nextNode;
		}

		return preNode;
	}

	/**
	 * 第2种遍历方式
	 * 		缺点：改变了原来的链表
	 * @param head
	 * @return
	 */
	static Node reverseByLoop2(Node head){
		//单链表为空或只有一个节点，直接返回原单链表，不用翻转了
		if (head == null || head.next == null){
			return head;
		}
		//最终要返回的链表结果
		Node result = null;
		while (head!=null){
			//遍历原链表，指针域指向下个节点
			Node temp = head.next;
			//将当前节点指针域指向正在组装的反向链表
			head.next = result;
			//将组装的反向链表赋值给目标变量
			result = head;
			//重新赋值原链表的下个节点，进行遍历
			head = temp;
		}
		return result;
	}

	/**
	 * 借助栈的特点实现翻转打印
	 * 	缺点：需要两次遍历
	 * @param head
	 */
	static void reverseByStackAndPrint(Node head){
		//单链表为空或只有一个节点，直接返回原单链表，不用翻转了
		if (head == null || head.next == null){
			System.out.println(head.data);
			return;
		}
		Stack<Node> nodes = new Stack<>();
		//压栈
		while (head!=null){
			Node temp = head.next;
			head.next = null;
			nodes.push(head);
			head = temp;
		}
		//打印
		StringBuilder sb = new StringBuilder();
		while (nodes.size()>0){
			Node node = nodes.pop();
			if (nodes.size()==0){
				sb.append(""+node.data+"");
				break;
			}
			sb.append(""+node.data+"->");
		}
		System.out.println(sb.toString());
	}

	/**
	 * 打印节点数据
	 */
	static void printNode(Node node){
		StringBuilder sb = new StringBuilder();
		if (node==null){
			System.out.println("链表节点为空..");
		}
		Node temp = node;
		while (true){
			if (temp.next==null){
				sb.append(""+temp.data+"");
				break;
			}
			sb.append(""+temp.data+"->");
			temp = temp.next;
		}
		System.out.println(sb.toString());
	}

	/**
	 * 定义一个链表节点类
	 */
	static class Node {
		/**数据域*/
		int data;
		/**指针域*/
		Node next;
		Node(int data){
			this.data = data;
		}
	}
}
