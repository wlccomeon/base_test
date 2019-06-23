package com.lc.test.thread.collection.concurrentmodify;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 集合的并发修改异常测试
 * @author: wlc
 * @date: 2019/6/19 0019 18:14
 **/
public class CollectionModify {

	public static void main(String[] args) {
		CollectionModify collectionModify = new CollectionModify();

		//对线程不安全的集合类进行并发修改：
		List<String> list = new ArrayList<>();
		for (int i=1; i<=500; i++){
			collectionModify.startListThread(list,i);
		}
		try {
		    TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("list.size--->>>"+list.size());
		//结果：list中得到的结果不对。。
		//list.size--->>>499
		//造成这个的原因通过追踪源码大致可以猜测会出现在两个地方：
			//1.arrayList在进行扩容的时候
			//2.add方法的elementData[size++] = e;size++是不安全的，有可能并发添加导致丢数据







		//出现异常：java.util.ConcurrentModificationException
		//导致原因：
			//并发争抢修改：一个线程在修改集合的时候，另一个线程强行介入，同时进行修改。
		//解决方式：
			//1.new Vector()
				//锁加载了方法上
				//public synchronized boolean add(E e) {
				//   modCount++;
				//   ensureCapacityHelper(elementCount + 1);
				//   elementData[elementCount++] = e;
				//   return true;
				//}
			//2.Collections.synchronizedList(new ArrayList<>())
				//锁加载了代码块上
				//       public void add(int index, E element) {
				//            synchronized (mutex) {list.add(index, element);}
				//        }
			//3.new CopyOnWriteArrayList<>()
				//使用了读写分离锁，并且每添加一个元素，就复制一份新的集合，在新的集合上修改完毕之后，修改原来的引用到新的集合上。
				//public boolean add(E e) {
				//    final ReentrantLock lock = this.lock;
				//    lock.lock();
				//    try {
				//        Object[] elements = getArray();
				//        int len = elements.length;
				//        Object[] newElements = Arrays.copyOf(elements, len + 1);
				//        newElements[len] = e;
				//        setArray(newElements);
				//        return true;
				//    } finally {
				//        lock.unlock();
				//    }
				//}
			//4.其他集合
				//针对hashset集合，它的底层实现是hashmap，所以也是不安全的。与上面类似，也具有通用的Collections.synchronizedSet()和CopyOnWriteArraySet<>类
				//针对hashmap，他的并发线程安全方式是Collections.synchronizedMap()和concurrentHashmap()，它没有copyonwrite类
		//优化建议：
			//CopyOnWriteArrayList的缺点也很明显：如果集合中存放的是大对象，那么很耗内存
			//根据业务特点选择使用Collections.synchronizedList或CopyOnWriteArrayList<>

//		collectionModify.vectorConcurrentModify();
//		collectionModify.synchronizedListModify();
//		collectionModify.copyOnWriteList();
	}

	/**
	 * 使用vector进行并发修改
	 */
	public void vectorConcurrentModify(){
		List<String> list = new Vector<>();;
		for (int i=1; i<=30; i++){
			startListThread(list,i);
		}
	}

	/**
	 * 使用集合工具类Collections自带的synchronizedList
	 */
	public void synchronizedListModify(){
		List<String> list = Collections.synchronizedList(new ArrayList<>());
		for (int i=1; i<=30; i++){
			startListThread(list,i);
		}
	}

	/**
	 * 使用juc包下的写时复制容器 CopyOnWriteArrayList
	 */
	public void copyOnWriteList(){
		List<String> list = new CopyOnWriteArrayList<>();
		for (int i=1; i<=30; i++){
			startListThread(list,i);
		}
	}

	/**
	 * 对list集合进行并发修改
	 * @param list list集合
	 * @param i 第几个线程
	 */
	public void startListThread(List<String> list,Integer i){
		new Thread(()->{
			list.add(UUID.randomUUID().toString().substring(0,8));
			//下面的打印如果不去掉的话，会报java.util.ConcurrentModificationException错误
			//原因：打印的时候，集合的toString()方法使用的Iterator遍历并获取元素，StringBuilder进行拼接，而Iterator遍历时，是不允许修改集合的长度的。
//			System.out.println(Thread.currentThread().getName()+"-->"+list);
		},String.valueOf(i)).start();
	}

	/**
	 * hashset的安全性
	 */
	public void HashSetSecurity(){
		Set<String> set = new HashSet<>();
		//这个其实跟arrayList中存在的并发修改问题一样
		//扩充：
			//hashSet底层就是hashMap，只不过hashSet在add的时候，通过hashmap put的时候，key为add的值，而value为一个常量对象。
	}
}
