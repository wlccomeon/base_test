package com.lc.test.thread.lock.demo.countdownlatch;

import org.apache.commons.lang3.RandomUtils;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description: SemaPhore信号的测试。当其拥有的资源数量为1时，其作用就类似synchronized了。
 * 				 以争抢车位为例，车位(3)少于车(6)数量。某些车正抢到车位一段时间之后离开，那么未争抢到车就可以继续争抢了。
 * @author: wlc
 * @date: 2019/6/20 0020 19:24
 **/
public class SemaPhoreTest {


	public static void main(String[] args) {

		/**使用semaphore模拟拥有3个车位资源*/
		Semaphore semaphore = new Semaphore(3);

		//6个车争抢车位
		for (int i=1; i<= 6; i++){
		    new Thread(()->{
				try {
					//获取到车位
					semaphore.acquire();
					System.out.println(Thread.currentThread().getName()+"\t 争抢到车位");
					try {
						//停车位使用几秒钟就开走
						int sec = RandomUtils.nextInt(1,5);
					    TimeUnit.SECONDS.sleep(sec);
						System.out.println(Thread.currentThread().getName()+"\t 停车位"+sec+"秒后使用完毕");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					//车开走之后，释放车位
					semaphore.release();
				}
		    },String.valueOf(i)).start();
		}

		//result:
		//2	 争抢到车位
		//3	 争抢到车位
		//1	 争抢到车位
		//1	 停车位1秒后使用完毕
		//4	 争抢到车位
		//2	 停车位3秒后使用完毕
		//5	 争抢到车位
		//3	 停车位4秒后使用完毕
		//6	 争抢到车位
		//4	 停车位4秒后使用完毕
		//5	 停车位2秒后使用完毕
		//6	 停车位2秒后使用完毕

	}

}
