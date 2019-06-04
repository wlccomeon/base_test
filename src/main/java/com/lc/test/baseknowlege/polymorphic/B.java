package com.lc.test.baseknowlege.polymorphic;

public class B extends A{
	public String show(B obj){
		return ("B and B");
	}

	@Override
	public String show(A obj){
		return ("B and A");
	}
}
