package com.lc.test.designpattern.templatemethod;

/**
 * @desc 存款者
 * @author wlc
 * @date 2020-01-11 13:42:54
 */
public class Saver extends BankTemplate{
	/**
	 * 业务处理
	 */
	@Override
	protected void dealService() {
		System.out.println("我来存款...");
	}
}
