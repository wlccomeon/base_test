package com.lc.test.baseknowlege.override;

import lombok.extern.java.Log;


/**
 * 重写复习：子类
 * @author wlc
 *
 */
@Log
public class Child extends Parent {

/**
 * ①：子类访问级别不能低于父类访问级别
 * 下面的protected或private是不允许的，直接报错。
 */
//	@Override
//	protected void getName() {
//		super.getName();
//	}


	/**
	 * ②：子类重写的方法中抛出的异常不能高于父类
	 * 例如父类的getName抛出了RuntimeException，那么这里是不能抛出Exception的。
	 * @throws Exception
	 */
//	@Override
//	public void getName() throws Exception{
//		super.getName();
//	}

	/**
	 * ③：子类重写的方法，方法名、参数、返回类型必须跟父类中的方法保持一致。
	 * 例如下例中的参数不一致。
	 * @param name
	 */
//	@Override
//	public void getName(String name){
//		super.getName();
//	}

	@Override
	public void getName() {
		super.getName();
	}

	@Override
	public void getAddress() {
		super.getAddress();
	}
}
