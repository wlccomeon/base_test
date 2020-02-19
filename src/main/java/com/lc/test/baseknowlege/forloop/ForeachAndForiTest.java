package com.lc.test.baseknowlege.forloop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @desc foreach 和 fori 的对比以及适用场合
 * collection实现了Iterable接口
	 * 1. fori 删除(顺序会漏删，倒序可正常)
	 * 顺序删除
	 * 倒序删除
	 * 2. foreach 删除(删除一个元素停止遍历可正常，多个元素删除有CME问题)
	 * 3. iterator 删除(可正常删除)
 * @author wlc
 * @date 2020-02-05 16:11:50
 */
public class ForeachAndForiTest {

	private static List<String> list = new ArrayList<>(5);
	static {
		list.add("add");
		list.add("delete");
		list.add("delete");
		list.add("update");
		list.add("query");
	}

	/**
	 * 由于数组移动的特点，顺序删除与倒序删除结果不同
	 */
	@Test
	public void foriDelete(){
//		for (int i = 0; i < list.size(); i++) {
//			if ("delete".equals(list.get(i))){
//				list.remove(i);
//			}
//		}
		//顺序删除之后的结果为-->[add, delete, update, query]
//		System.out.println("顺序删除之后的结果为-->"+list.toString());
		//倒序删除：删除时，数据的移动方向与索引的方向是一致的，不会导致漏删
		for (int i = list.size() - 1; i >= 0; i--) {
			if ("delete".equals(list.get(i))){
				list.remove(i);
			}
		}
		System.out.println("倒序删除之后的结果为-->"+list.toString());
		//倒序删除之后的结果为-->[add, update, query]
	}

	/**
	 * 可删除元素，若删除元素后继续循环则会报ConcurrentModificationException异常
	 * 本质是将foreach转换成了iterator的hasnext和next方法进行循环
	 */
	@Test
	public void foreachDelete(){
		for(String s:list){
			if(s.equals("delete")){
				//注意，这里的remove方法是list本身的，不是iterator的remove
				list.remove(s);
			}
		}
		System.out.println("foreach删除之后的结果为-->"+list.toString());
		//编译器转换成的代码：
		//Iterator var1 = list.iterator();
		//
		//        while(var1.hasNext()) {
		//            String s = (String)var1.next();
		//            if (s.equals("delete")) {
		//                list.remove(s);
		//            }
		//        }
		//
		//        System.out.println("foreach删除之后的结果为-->" + list.toString());
	}

	/**
	 * 迭代器，可以正常的删除。
	 */
	@Test
	public void iteratorDelete(){
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			String x = it.next();
			if(x.equals("delete")){
				it.remove();
			}
		}
		//iterator删除之后的结果为-->[add, update, query]
		System.out.println("iterator删除之后的结果为-->"+list.toString());
	}


}
