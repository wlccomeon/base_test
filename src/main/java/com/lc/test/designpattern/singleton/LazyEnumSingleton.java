package com.lc.test.designpattern.singleton;

/**
 * @Description: 懒加载形式的枚举单例,类似于静态内部类的方式
 * @author: wlc
 * @date: 2019/6/24 0024 7:58
 **/
public class  LazyEnumSingleton {

	private LazyEnumSingleton(){};

	/**
	 * 内部类形式的枚举，可起到懒加载的类似效果
	 */
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

	/**
	 * 提供单例的静态方法
	 */
	public static LazyEnumSingleton getInstance(){
		return LazyEnumHolder.INSTANCE.getSingleton();
	}

}
