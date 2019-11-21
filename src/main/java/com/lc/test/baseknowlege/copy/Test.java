package com.lc.test.baseknowlege.copy;

/**
 * @author wlc
 * @desc
 * @date 2019-09-29 21:16:27
 */
public class Test {

	@org.junit.jupiter.api.Test
	public void test() throws Exception{
		Dog pet = new Dog("jingba");
		Person p1 = new Person("xiaoming", pet);
		System.out.println(p1.getPet());
		System.out.println(p1.getPet().getName());
		Person p2 = (Person) ObjectCopyUtil.deeplyCopy(p1);
		System.out.println(p2.getPet());
		System.out.println(p2.getPet().getName());
	}

}
