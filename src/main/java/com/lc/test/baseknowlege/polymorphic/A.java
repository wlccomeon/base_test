package com.lc.test.baseknowlege.polymorphic;

/**
 * 多态的测试
 */
public class A {
	public String show(D obj) {
		return ("A and D");
	}

	public String show(A obj) {
		return ("A and A");
	}
}
