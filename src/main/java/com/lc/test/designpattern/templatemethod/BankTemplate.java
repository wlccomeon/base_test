package com.lc.test.designpattern.templatemethod;

/**
 * @desc
 * @author wlc
 * @date 2020-01-11 13:44:44
 */
public abstract class BankTemplate {
	private void getNum(){
		System.out.println("排队取号...");
	}
	/**业务处理,具体的实现流程交给子类，也就是钩子方法*/
	protected abstract void dealService();
	private void feedBack(){
		System.out.println("反馈评价...");
	}

	/**
	 * 定义好一个流程模板，不可更改
	 */
	protected final void doBankProcessFlow(){
		this.getNum();
		this.dealService();
		this.feedBack();
	}
}
