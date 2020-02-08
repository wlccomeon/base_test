package com.lc.test.datastructure.linkedlist;

import java.util.Objects;

/**
 * @desc 梁山好汉实体类
 * 		 重写equals和hashcode方法，通过rank字段判断两个英雄实体是否相同
 * @author wlc
 * @date 2020-02-08 11:23:07
 */
public class Hero {
	/**好汉排名*/
	private int rank;
	/**好汉名字*/
	private String name;
	/**好汉外号*/
	private String nickName;

	public Hero(int rank, String name, String nickName) {
		this.rank = rank;
		this.name = name;
		this.nickName = nickName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Hero hero = (Hero) o;
		return rank == hero.rank;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank);
	}

	@Override
	public String toString() {
		return "Hero{" +
				"rank=" + rank +
				", name='" + name + '\'' +
				", nickName='" + nickName + '\'' +
				'}';
	}
}
