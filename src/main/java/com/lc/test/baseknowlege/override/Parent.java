package com.lc.test.baseknowlege.override;

import lombok.extern.java.Log;

/**
 * 复习重写
 * @author wlc
 */
@Log
public class Parent {

	public void getName() throws RuntimeException{
		log.info("加载父类Name");
	}

	public void getAddress(){
		log.info("加载父类Address");
	}
}
