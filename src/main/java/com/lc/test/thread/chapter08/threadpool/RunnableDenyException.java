package com.lc.test.thread.chapter08.threadpool;

/**
 * @Description: 定义一个拒绝策略的Exception
 * @author: wlc
 * @date: 2019/6/9 0009 21:04
 **/
public class RunnableDenyException extends RuntimeException {
	public RunnableDenyException(String msg) {
		super(msg);
	}
}
