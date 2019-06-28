package com.lc.test.designpattern.singleton;

/**
 * @Description: 懒加载形式的枚举单例,类似于静态内部类的方式
 * @author: wlc
 * @date: 2019/6/24 0024 7:58
 **/
public class  LazyEnumSingleton {

	private byte[] data = new byte[1024];

	private LazyEnumSingleton(){};

	private enum LazyEnumHolder{
		INSTANCE;

		private LazyEnumSingleton instance;

		LazyEnumHolder() {
			this.instance = new LazyEnumSingleton();
		}

		private LazyEnumSingleton getSingleton(){
			return this.instance;
		}
	}

	public static LazyEnumSingleton getInstance(){
		return LazyEnumHolder.INSTANCE.getSingleton();
	}


}
