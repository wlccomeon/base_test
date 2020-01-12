package com.lc.test.designpattern.templatemethod;

/**
 * @desc 取款者
 * @author wlc
 * @date 2020-01-11 13:44:15
 */
public class Withdrawer extends BankTemplate{
	/**
	 * 业务处理
	 */
	@Override
	protected void dealService() {
		System.out.println("我来取款...");
	}
}
