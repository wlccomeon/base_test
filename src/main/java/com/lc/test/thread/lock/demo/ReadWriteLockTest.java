package com.lc.test.thread.lock.demo;

import com.mysql.cj.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 读写锁测试
 * @author: wlc
 * @date: 2019/6/20 0020 10:56
 **/
public class ReadWriteLockTest {

		public static void main(String[] args) throws InterruptedException {
		MyCache myCache = new MyCache();
		ReadWriteLockTest readWriteLock = new ReadWriteLockTest();

		//场景一：完全不加锁
		readWriteLock.noLockWrite(myCache);
		//确保先执行write
		TimeUnit.SECONDS.sleep(1);
		System.out.println("myCache.myMap 1= " + myCache.myMap);
		readWriteLock.noLockRead(myCache);
		TimeUnit.SECONDS.sleep(1);
		System.out.println("myCache.myMap 2= " + myCache.myMap);
		//result:
		//线程 1 正在写入 key 1...
		//线程 5 正在写入 key 5...
		//线程 4 正在写入 key 4...
		//线程 3 正在写入 key 3...
		//线程 2 正在写入 key 2...
		//线程 2 写入完毕 key 2...
		//线程 4 写入完毕 key 4...
		//线程 1 写入完毕 key 1...
		//线程 3 写入完毕 key 3...
		//线程 5 写入完毕 key 5...
		//myCache.myMap 1= {1=val1, 2=val2, 4=val4, 5=val5}
		//线程 1 正在读取 key 1...
		//线程 2 正在读取 key 2...
		//线程 1 读取完毕 key 1 val val1...
		//线程 4 正在读取 key 4...
		//线程 4 读取完毕 key 4 val val4...
		//线程 3 正在读取 key 3...
		//线程 3 读取完毕 key 3 val null...
		//线程 2 读取完毕 key 2 val val2...
		//线程 5 正在读取 key 5...
		//线程 5 读取完毕 key 5 val val5...
		//myCache.myMap 2= {1=val1, 2=val2, 4=val4, 5=val5}
		//结果可以看出，写锁并没有被独占，这是不符合要求的。写肯定要保持原子性的。
		// 而且明显最后的结果根本不正确，只有key为2的线程写入了值，别的都没有。

		//场景二：使用读写分离锁
//		readWriteLock.useWriteLock(myCache);
//		readWriteLock.useReadLock(myCache);
		//result:其中之一：
		//线程 4 正在写入 key 4...
		//线程 4 写入完毕 key 4...
		//线程 3 正在写入 key 3...
		//线程 3 写入完毕 key 3...
		//线程 2 正在写入 key 2...
		//线程 2 写入完毕 key 2...
		//线程 5 正在写入 key 5...
		//线程 5 写入完毕 key 5...
		//线程 1 正在写入 key 1...
		//线程 1 写入完毕 key 1...
		//线程 1 正在读取 key 1...
		//线程 2 正在读取 key 2...
		//线程 3 正在读取 key 3...
		//线程 4 正在读取 key 4...
		//线程 5 正在读取 key 5...
		//线程 1 读取完毕 key 1 val val1...
		//线程 2 读取完毕 key 2 val val2...
		//线程 3 读取完毕 key 3 val val3...
		//线程 4 读取完毕 key 4 val val4...
		//线程 5 读取完毕 key 5 val val5...
		//以上可以看出：写入的过程是严格配对的，可以保证不被打扰。而读的过程则没有限制，也就是说是支持并发读的。
	}

	/**
	 * 不加锁多线程读数据
	 */
	public void noLockRead(MyCache myCache){
		for (int i = 1; i <= 5; i++) {
			int key = i;
			new Thread(()->{
				myCache.get(key);
			},String.valueOf(i)).start();
		}
	}

	/**
	 * 不加锁多线程写数据
	 */
	public void noLockWrite(MyCache myCache){
		for (int i=1; i<=5; i++){
			int key = i;
		    new Thread(()->{
		        myCache.put(key,"val"+key);
		    },String.valueOf(i)).start();
		}
	}


	/**
	 * 使用读写分离锁中的写锁
	 */
	public void useWriteLock(MyCache myCache){
		for (int i=1; i<=5; i++){
			int key = i;
			new Thread(()->{
				myCache.rwLockPut(key,"val"+key);
			},String.valueOf(i)).start();
		}
	}

	public void useReadLock(MyCache myCache){
		for (int i = 1; i <= 5; i++) {
			int key = i;
			new Thread(()->{
				myCache.rwLockGet(key);
			},String.valueOf(i)).start();
		}
	}

}

/**
 * 简单模拟缓存操作类
 */
class MyCache{
	/**volatile保证可见性*/
	volatile Map<Integer,Object> myMap = new HashMap<>();
	/**使用读写分离锁（属于可重入锁的一种）*/
	ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	/**对hashmap进行写入操作*/
	public void put(Integer key,Object val){
		System.out.println("线程 "+Thread.currentThread().getName()+" 正在写入 key "+key+"...");
		//模拟网络拥堵情况，睡眠10ms，如果不加锁，可能会导致其他线程强行插入该过程
		try {
		    TimeUnit.MILLISECONDS.sleep(10);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		myMap.put(key,val);
		System.out.println("线程 "+Thread.currentThread().getName()+" 写入完毕 key "+key+"...");
	}

	/**对hashmap进行读取操作*/
	public void get(Integer key){
		System.out.println("线程 "+Thread.currentThread().getName()+" 正在读取 key "+key+"...");
		myMap.get(key);
		System.out.println("线程 "+Thread.currentThread().getName()+" 读取完毕 key "+key+" val "+myMap.get(key)+"...");
	}

	/**使用读写锁之写锁对hashmap进行写入操作*/
	public void rwLockPut(Integer key,Object val){
		rwLock.writeLock().lock();
		try {
			System.out.println("线程 "+Thread.currentThread().getName()+" 正在写入 key "+key+"...");
			//模拟网络拥堵情况，睡眠200ms，如果不加锁，可能会导致其他线程强行插入该过程
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			myMap.put(key,val);
			System.out.println("线程 "+Thread.currentThread().getName()+" 写入完毕 key "+key+"...");
		}catch (Exception e){
		    e.printStackTrace();
		}finally {
		    rwLock.writeLock().unlock();
		}

	}
	/**使用读写锁之读锁对hashmap进行读取操作*/
	public void rwLockGet(Integer key){
		rwLock.readLock().lock();
		try {
			System.out.println("线程 "+Thread.currentThread().getName()+" 正在读取 key "+key+"...");
			//这里也可以设置线程睡眠200秒，模拟网络拥堵情况，测试是否可以并发读。
			try {
			    TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			myMap.get(key);
			System.out.println("线程 "+Thread.currentThread().getName()+" 读取完毕 key "+key+" val "+myMap.get(key)+"...");
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			rwLock.readLock().unlock();
		}
	}
}