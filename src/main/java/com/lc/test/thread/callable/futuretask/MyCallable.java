package com.lc.test.thread.callable.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/6/23 0023 14:34
 **/
public class MyCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println("---------"+Thread.currentThread().getName()+" comes in mycallable----------- ");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("----------mycallable is done------------");
		return 1024;
	}
}
