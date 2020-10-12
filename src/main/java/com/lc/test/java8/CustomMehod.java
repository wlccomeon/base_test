package com.lc.test.java8;

import com.lc.test.entity.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @desc 自定义通用Java8方法
 * @author wlc
 * @date 2020-09-04 14:52:44
 */
public class CustomMehod {


	public static void main(String[] args) {
		List<User> users = new LinkedList<>();
		users.add(new User(1,"Jim",1,"japan"));
		users.add(new User(1,"Jim",1,"china"));
		users.add(new User(1,"Leo",1,"Jim"));
		users.add(new User(1,"Josh",1,"Jim"));

		List<User> collect = users.stream().distinct().map(user -> {user.setSex(222);return user;}).collect(Collectors.toList());

		System.out.println(collect);
	}


	/**
	 * 去重方法
	 * @param keyExtractor
	 * @param <T>
	 * @return
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
	}

}
