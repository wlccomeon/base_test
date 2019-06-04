package com.lc.test.thread.chapter01;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 启动thread
 * @author wlc
 */
public class FirstThreadTest {

	/**
	 * 这种调用方式中，listenMusic方法将永远不会被调用到
	 * 		因为browseNews方法一直在执行，不会结束。
	 */
	@Test
	public void onlyBrowseNews(){
		browseNews();
		listenMusic();
		//result:
		//浏览网页
		//浏览网页
		//浏览网页
		//浏览网页
		//浏览网页
	}

	/**
	 * 可以实现听音乐与浏览网页同时进行的方法
	 * 		问题1：并不能严格的按照先后顺序执行两个方法
	 * 		问题2：创建并启动thread的方法必须在browseNews方法之前，否则执行不到创建。
	 */
	@Test
	public void BrowseNewsAndListenMusic(){
		new Thread(){
			@Override
			public void run() {
				listenMusic();
			}
		}.start();
		browseNews();

		//result:
		//浏览网页
		//听音乐
		//听音乐
		//浏览网页
		//浏览网页
		//听音乐
		//听音乐
		//浏览网页
		//听音乐
	}



	/**
	 * 一直浏览网页方法
	 */
	public void browseNews(){
		for (;;){
			System.out.println("浏览网页");
			sleep(1);
		}
	}

	/**
	 * 一直听音乐方法
	 */
	public void listenMusic(){
		for (;;){
			System.out.println("听音乐");
			sleep(1);
		}
	}


	/**
	 * 睡眠方法
	 * @param seconds
	 */
	public void sleep(int seconds){
		try {
			TimeUnit.SECONDS.sleep(seconds);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
