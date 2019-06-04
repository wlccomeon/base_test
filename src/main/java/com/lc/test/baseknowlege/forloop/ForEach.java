package com.lc.test.baseknowlege.forloop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ForEach {


	/**
	 * 测试for循环和增强for循环对数组和链表的循环性能
	 */
	@Test
	public void testForLoop(){
		//实例化arrayList
		List<Integer> arrayList = new ArrayList<Integer>();
		//实例化linkList
		List<Integer> linkList = new LinkedList<Integer>();

		//插入10万条数据
		for (int i = 0; i < 100000; i++) {
			arrayList.add(i);
			linkList.add(i);
		}

		int array = 0;
		//用for循环arrayList
		long arrayForStartTime = System.currentTimeMillis();
		for (int i = 0; i < arrayList.size(); i++) {
			array = arrayList.get(i);
		}
		long arrayForEndTime = System.currentTimeMillis();
		System.out.println("用for循环arrayList 10万次花费时间：" + (arrayForEndTime - arrayForStartTime) + "毫秒");

		//用foreach循环arrayList
		long arrayForeachStartTime = System.currentTimeMillis();
		for(Integer in : arrayList){
			array = in;
		}
		long arrayForeachEndTime = System.currentTimeMillis();
		System.out.println("用foreach循环arrayList 10万次花费时间：" + (arrayForeachEndTime - arrayForeachStartTime ) + "毫秒");

		//用for循环linkList
		long linkForStartTime = System.currentTimeMillis();
		int link = 0;
		for (int i = 0; i < linkList.size(); i++) {
			link = linkList.get(i);
		}
		long linkForEndTime = System.currentTimeMillis();
		System.out.println("用for循环linkList 10万次花费时间：" + (linkForEndTime - linkForStartTime) + "毫秒");

		//用froeach循环linkList
		long linkForeachStartTime = System.currentTimeMillis();
		for(Integer in : linkList){
			link = in;
		}
		long linkForeachEndTime = System.currentTimeMillis();
		System.out.println("用foreach循环linkList 10万次花费时间：" + (linkForeachEndTime - linkForeachStartTime ) + "毫秒");

		//结果：
		//用for循环arrayList 10万次花费时间：5毫秒
		//用foreach循环arrayList 10万次花费时间：12毫秒
		//用for循环linkList 10万次花费时间：15274毫秒
		//用foreach循环linkList 10万次花费时间：5毫秒
	}

	/**
	 * 测试iterator迭代，出现的问题，及解决方法
	 */
	@Test
	public void testIterator(){

		// Create a link list which stores integer elements
		List<Integer> l = new LinkedList<Integer>();

		// Now add elements to the Link List
		l.add(2);
		l.add(3);
		l.add(4);

		// Make another Link List which stores integer elements
		List<Integer> s=new LinkedList<Integer>();
		s.add(7);
		s.add(8);
		s.add(9);

		// Iterator to iterate over a Link List
		for (Iterator<Integer> itr1 = l.iterator(); itr1.hasNext(); )
		{
			for (Iterator<Integer> itr2=s.iterator(); itr2.hasNext(); )
			{
				Integer i1 = itr1.next();
				Integer i2 = itr2.next();
				if (i1<i2)
				{
					System.out.println(i1);
				}
			}
		}

		//结果：java.util.NoSuchElementException
		//	at java.util.LinkedList$ListItr.next(LinkedList.java:890)
		//	at com.lc.test.baseknowlege.forloop.ForEach.testIterator(ForEach.java:96)

		//原因：对itr1进行了两重迭代，超过了itr1的元素个数

		//解决方法：
		// ①：在iterator循环的时候定义局部变量，减少迭代次数
		// 弊端：虽然解决了NoSuchElementException的错误，但是在外层for循环的第二次的时候
		//for (Iterator<Integer> itr1 = l.iterator(); itr1.hasNext(); )
		//		{
		//			for (Iterator<Integer> itr2=s.iterator(); itr2.hasNext(); )
		//			{
		//				Integer i1 = itr1.next();
		//				Integer i2 = itr2.next();
		//				if (i1<i2)
		//				{
		//					System.out.println(i1);
		//				}
		//			}
		//		}
		// ②：使用foreach循环
		for (int a:l)
		{
			for (int b:s)
			{
				if (a<b){
					System.out.print(a + " ");
				}
			}
		}
	}

}
