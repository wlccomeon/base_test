package com.lc.test.datastructure.linkedlist.single;

import com.lc.test.datastructure.linkedlist.Hero;

/**
 * @desc 简单单链表测试
 * @author wlc
 * @date 2020-02-08 15:40:31
 */
public class SingleLinkedListTest {
	/**初始化数据*/
	static SingleLinkedList<Hero> heros = new SingleLinkedList<>();
	static {
		Hero songjiang = new Hero(1,"宋江","呼保义");
		Hero lujunyi = new Hero(2,"卢俊义","玉麒麟");
		Hero wuyong = new Hero(3,"吴勇","智多星");
		Hero gongsunsheng = new Hero(4,"公孙胜","入云龙");
		heros.add(lujunyi);
		heros.add(wuyong);
		heros.add(songjiang);
		heros.add(gongsunsheng);
	}

	public static void main(String[] args) {

		System.out.println("============链表初始数据======================");
		heros.printEles();
		System.out.println("==============链表修改数据====================");
		Hero newSongjiang = new Hero(1,"宋江","及时雨");
		heros.modify(newSongjiang);
		heros.printEles();
		System.out.println("==============链表删除数据====================");
		//删除边界中的第1个
		heros.delete(new Hero(1,"宋江","呼保义"));
//		heros.delete(new Hero(3,"吴勇","智多星"));
		//删除边界中的最后1个
		heros.delete(new Hero(4,"公孙胜","入云龙"));
		heros.printEles();
		//结果：
		//============链表初始数据======================
		//Hero{rank=2, name='卢俊义', nickName='玉麒麟'}
		//Hero{rank=3, name='吴勇', nickName='智多星'}
		//Hero{rank=1, name='宋江', nickName='呼保义'}
		//Hero{rank=4, name='公孙胜', nickName='入云龙'}
		//==============链表修改数据====================
		//Hero{rank=2, name='卢俊义', nickName='玉麒麟'}
		//Hero{rank=3, name='吴勇', nickName='智多星'}
		//Hero{rank=1, name='宋江', nickName='及时雨'}
		//Hero{rank=4, name='公孙胜', nickName='入云龙'}
		//==============链表删除数据====================
		//Hero{rank=3, name='吴勇', nickName='智多星'}
		//Hero{rank=1, name='宋江', nickName='及时雨'}
	}


}
