package com.lc.test.thread.lock.demo.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 使用countdownlatch加枚举，模拟秦灭六国，一统华夏。
 * 					countdownlatch用来确保除main之外所有的线程都执行完。
 * @author: wlc
 * @date: 2019/6/20 0020 17:41
 **/
public class CountDownLatchTest {

	public static void main(String[] args) {

		/**使用countdownlatch，设置一个初始值*/
		CountDownLatch cdl = new CountDownLatch(6);

		//启动6个线程，模拟六国被灭
		for (int i=1; i<= 6; i++){
		    new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"\t国被灭");
				cdl.countDown();
		    },CountriesEnum.getValue(i)).start();
		}

		//如果cdl计数器没有归零，则继续等待
		try {
			//可以设置等待过期时间，不设置则一直等待。
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("秦灭六国，华夏大一统实现...");

		//result:
		//魏	国被灭
		//齐	国被灭
		//燕	国被灭
		//楚	国被灭
		//韩	国被灭
		//赵	国被灭
		//秦灭六国，华夏大一统实现...
	}

}
