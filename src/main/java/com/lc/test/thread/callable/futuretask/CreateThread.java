package com.lc.test.thread.callable.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/6/23 0023 14:39
 **/
public class CreateThread {

	public static void main(String[] args) {

		MyCallable myCallable = new MyCallable();

		FutureTask<Integer> futureTask = new FutureTask<Integer>(myCallable);

		Thread t1 = new Thread(futureTask,"BB");
		Thread t2 = new Thread(futureTask,"AA");
		t2.start();
		t1.start();

		//如果futureTask中的任务没有结束，则等待
		while (!futureTask.isDone()){
			try {
			    TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			System.out.println("I'm waiting the task gets done...");
		}
		try {
			int callableResult = futureTask.get();
			System.out.println("callable result is "+callableResult);
			System.out.println("the finally result is :"+(callableResult+100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		//结果：
		//---------AA comes in mycallable-----------
		//I'm waiting the task gets done...
		//I'm waiting the task gets done...
		//...... 省略
		//I'm waiting the task gets done...
		//I'm waiting the task gets done...
		//----------mycallable is done------------
		//I'm waiting the task gets done...
		//callable result is 1024
		//the finally result is :1124

		//说明：如果new多个thread，穿进去的同一个futureTask，这个任务是只会执行一遍的。
		//如果想要执行多遍，那么需要传另外一个futureTask实例。

	}

}
