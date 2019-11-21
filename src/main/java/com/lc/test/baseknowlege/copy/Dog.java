package com.lc.test.baseknowlege.copy;

import java.io.Serializable;

public class Dog implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dog(String name) {
		super();
		this.name = name;
	}

	public Dog() {
		super();
	}

}