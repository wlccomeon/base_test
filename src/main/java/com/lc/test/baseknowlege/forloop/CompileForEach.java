package com.lc.test.baseknowlege.forloop;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class CompileForEach {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(3);
		list.add(4);

		System.out.println("------------------");
		for (Integer i : list){
			System.out.println(i);
//			if (i==3){
//				list.add(5);
//			}
			//结果：Exception in thread "main" java.util.ConcurrentModificationException
			//原因：增强for循环底层是使用的iterator，而iterator是依赖于集合的，
			//		在使用iterator进行遍历的时候，集合是不允许修改的。
		}
		//解决方法：
		//		① 使用普通for循环
		System.out.println("------------------");
		for (int i=0;i<list.size();i++){
			int result = list.get(i);
			if (result==3){
				list.add(5);
			}
			System.out.println(result);
		}
		//		② 使用Iterator（只有remove方法，没有add方法）的子类ListIterator（有该子类独有的add方法）进行添加
		ListIterator<Integer> listIterator = list.listIterator();
		System.out.println("------------------");
		while (listIterator.hasNext()){
			Integer result = listIterator.next();
			if (result.equals(3)){
				listIterator.add(5);
			}
			System.out.println(result);
		}

	}

}
