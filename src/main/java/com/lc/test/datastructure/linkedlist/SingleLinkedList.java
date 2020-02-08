package com.lc.test.datastructure.linkedlist;

/**
 * @desc 单链表模拟实现
 * 		  链表节点分为 数据域 与 指针域
 * 		  单链表，指针域只有一个next(next其实就是一个链表节点)
 * 		  head节点不存储数据，作为链表的头结点
 * 		  注意：
 * 		  	下面的修改和删除方法中判断元素是否相等的时候是按照重写的hashcode和equals方法来的(其实两个元素并不相等，只是某些属性相同，在这里跟业务耦合了，暂时没想到其他方法)
 * @author wlc
 * @date 2020-02-08 11:01:48
 */
public class SingleLinkedList<E> {
	/**头节点，开始时不存储数据，当添加链表节点时，头结点的next域指向该节点*/
	private Node<E> head = new Node<>(null,null);

	/**
	 * 添加节点
	 * @param elment
	 */
	public void add(E elment){
		//定义一个临时变量，用来查找尾节点
		Node temp = head;
		//查找next为空的节点，也就是最后一个节点
		while(true){
			if (temp.next==null){
				break;
			}
			temp = temp.next;
		}
		//找到该节点之后，将节点的指针域指向最新的节点
		temp.next = new Node(elment,null);
	}

	/**
	 * 修改节点
	 * @param elment 新的元素
	 */
	public void modify(E elment){
		Node temp = head.next;
		//如果为空，则说明只有头结点，无法修改数据
		if (temp == null){
			System.out.println("链表为空..无法修改");
			return;
		}
		//遍历查找到要修改的元素，若找不到则无法修改，找到了则重新赋值。
		while (true){
			if (temp==null){
				System.out.println("没有找到该元素，无法修改...");
				return;
			}
			if (temp.data.equals(elment)){
				temp.data = elment;
				break;
			}
			temp = temp.next;
		}
	}

	/**
	 * 删除节点
	 * @param element 待删除的元素
	 */
	public void delete(E element){
		//region 方法1，初始节点设置为头结点的下个节点
		/*
		//判断链表是否为空
		Node<E> temp = head.next;
		if (temp==null){
			System.out.println("链表为空..无法删除");
			return;
		}
		while (true){
			if (temp==null){
				System.out.println("没有找到该元素，无法修改...");
				break;
			}
			//判断第一个数据域与待修改元素是否相等
			if (temp.data.equals(element)){
				//将头结点指针域指向该元素的下个节点(不管下个节点是否为空)
				head.next = temp.next;
				break;
			}
			//定义找到的节点
			Node<E> target = temp.next;
			if (target!=null && target.data.equals(element)) {
				//待删除节点的上个节点的指针域指向待删除节点的下个节点，待删除节点由于没有引用了，届时会被GC掉
				temp.next = temp.next.next;
				break;
			}
			temp = target;
		}*/
		//endregiondl

		//region 方法2,初始节点设置为头结点：
		Node<E> temp = head;
		boolean flag = false;//定义一个是否可以删除的标记
		while (true){
			//最后一个元素
			if (temp.next==null){
				break;
			}
			//找到待删除节点的前一个节点(不能找待删除的节点，因为否则没法找它的前置节点)
			if(temp.next.data.equals(element)){
				flag = true;
				break;
			}
			temp = temp.next;
		}
		if (flag){
			//将待删除元素的前置节点的指针域 指向 待删除元素的后置节点
			temp.next = temp.next.next;
		}else{
			System.out.println("没有找到待删除的元素..");
		}
		//endregion
	}



	/**
	 * 按照顺序打印单链表
	 */
	public void printEles(){
		//用来确保在有数据的时候，最后不打印 链表为空 的提示
		int num=0;
		//将头节点赋值给临时变量，进行遍历
		Node temp = head;
		while (true){
			if (temp.next==null){
				if (num==0){
					System.out.println("单链表为空，没有数据");
				}
				break;
			}
			num++;
			temp = temp.next;
			//打印数据域
			System.out.println(temp.data);
		}
	}


	/**
	 * 数据节点
	 */
	class Node<E>{
		/**数据域*/
		E data;
		/**指针域*/
		Node<E> next;

		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}

}
