package com.lc.test.baseknowlege.override.overload;

import lombok.extern.java.Log;

/**
 * 重载复习：
 * @author wlc
 */
@Log
public class OverLoad {
	public void sayHello(){
		log.info("hello!");
	}

	/**
	 * 不能根据返回类型来确定重载方法
	 * 例如下面的方法跟上面的方法只有返回类型不同，会报 sayHello is already defined错误
	 * @return
	 */
//	public String sayHello(){
//		log.info("hello String。");
//		return null;
//	}

	public void sayHello(String name){
		log.info(name+"sayHello ");
	}

	public void sayHello(String name,Integer num){
		log.info(name+"sayHello "+num.toString());
	}

	public void sayHello(Integer num,String name){

	}
}
