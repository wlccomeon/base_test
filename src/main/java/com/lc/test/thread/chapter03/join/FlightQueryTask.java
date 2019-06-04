package com.lc.test.thread.chapter03.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 自定义任务类：继承Thread和实现业务接口
 * @author wlc
 */
public class FlightQueryTask extends Thread implements FlightQuery {

	private final String origin;
	private final String destination;
	private final List<String> flightList = new ArrayList<>();

	/**
	 * 构造器,final变量的初始化，必须用构造器的方式。使用其他方式会报错。。。
	 * @param airline     航班
	 * @param origin	  出发地
	 * @param destination 目的地
	 */
	public FlightQueryTask(String airline,String origin,String destination) {
		//为线程命名
		super("["+airline+"]");
		this.origin = origin;
		this.destination = destination;
	}

	@Override
	public List<String> get() {
		return this.flightList;
	}

	@Override
	public void run() {
		System.out.printf("%s-query from %s to %s \n",getName(),origin,destination);
		int randomVal = ThreadLocalRandom.current().nextInt(10);
		try {
			TimeUnit.SECONDS.sleep(randomVal);
			this.flightList.add(getName()+"-"+randomVal);
			System.out.printf("The flight: %s list query successful \n",getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
