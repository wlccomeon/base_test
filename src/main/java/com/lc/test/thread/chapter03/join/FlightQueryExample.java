package com.lc.test.thread.chapter03.join;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 航班查询执行结果测试
 * @author wlc
 */
public class FlightQueryExample {

	private List<String> flightCompanies = Arrays.asList("海南航空","东方航空","南方航空");

	@Test
	public void queryFlight(){
		List<String> results = search("上海","北京");
		System.out.println("=================查询结果==========================");
		results.forEach(System.out::println);
	}


	/**
	 * 创建查询任务
	 * @param flight 航班
	 * @param origin 始发地
	 * @param destination 目的地
	 * @return
	 */
	public FlightQueryTask createSearchTask(String flight,String origin,String destination){
		return new FlightQueryTask(flight,origin,destination);
	}

	public List<String> search(String origin,String destination){
		final List<String> result = new ArrayList<>();

		//创建查询航班信息的线程列表
		List<FlightQueryTask> tasks = flightCompanies.stream()
				.map(f->createSearchTask(f,origin,destination))
				.collect(Collectors.toList());

		//分别启动这几个线程
		tasks.forEach(Thread::start);

		//分别调用每一个线程的join方法，阻塞当前线程
		tasks.forEach(t->{
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		//在此之前，当前线程发生阻塞，获取每一个查询线程的结果，并加入到result中
		tasks.stream().map(FlightQueryTask::get).forEach(result::addAll);

		return result;
	}




}
