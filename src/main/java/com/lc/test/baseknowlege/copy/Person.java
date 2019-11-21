package com.lc.test.baseknowlege.copy;
import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private Dog pet;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Dog getPet() {
		return pet;
	}
	public void setPet(Dog pet) {
		this.pet = pet;
	}
	public Person(String name, Dog pet) {
		super();
		this.name = name;
		this.pet = pet;
	}
	public Person() {
		super();
	}

}