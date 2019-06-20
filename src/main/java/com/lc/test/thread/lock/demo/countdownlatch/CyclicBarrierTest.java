package com.lc.test.thread.lock.demo.countdownlatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/6/20 0020 19:00
 **/
public class CyclicBarrierTest {

	public static void main(String[] args) {
		/**使用CyclicBarrier两个参数的构造器*/
		CyclicBarrier cyclicBarrier = new CyclicBarrier(6
				,()->{System.out.println("秦灭六国，华夏大一统实现...");});
		for (int i=1; i<=6; i++){
		    new Thread(()->{
				System.out.println(Thread.currentThread().getName()+"\t国被灭");
				try {
					//完成之后累加并等待
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			},CountriesEnum.getValue(i)).start();
		}

		//result:
		//魏	国被灭
		//齐	国被灭
		//燕	国被灭
		//楚	国被灭
		//赵	国被灭
		//韩	国被灭
		//秦灭六国，华夏大一统实现...
	}
}
