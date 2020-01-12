package com.lc.test.designpattern.templatemethod;

/**
 * @desc
 * @author wlc
 * @date 2020-01-11 13:43:08
 */
public class Main {

	public static void main(String[] args) {
		BankTemplate bt = new Saver();
		bt.doBankProcessFlow();
		System.out.println("**********************");
		bt = new Withdrawer();
		bt.doBankProcessFlow();
		//排队取号...
		//我来存款...
		//反馈评价...
		//**********************
		//排队取号...
		//我来取款...
		//反馈评价...
	}

}

