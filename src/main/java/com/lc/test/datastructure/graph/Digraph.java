package com.lc.test.datastructure.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/7/4 0004 7:40
 **/
public class Digraph {
	/**任务(顶点集合)*/
	private Set<Task> tasks;
	/**任务依赖关系集合,key是一个任务,value是它的前置任务集合
	 * 一个任务执行的前提是它在map中没有以它作为key的entry，或者是它的前置任务集合中的任务都是已执行的状态。*/
	private Map<Task, Set<Task>> map;

	/**在构造方法中初始化集合参数*/
	public Digraph() {
		this.tasks = new HashSet<Task>();
		this.map = new HashMap<Task, Set<Task>>();
	}

	/***
	 * 添加边(顶点关联)
	 * @param task 后置顶点
	 * @param prev 前置顶点
	 */
	public void addEdge(Task task, Task prev) {
		if (!tasks.contains(task) || !tasks.contains(prev)) {
			throw new IllegalArgumentException();
		}
		//获取当前任务的所有前置任务
		Set<Task> prevs = map.get(task);
		//如果前置任务为空，则创建前置任务为空的关系组合
		if (prevs == null) {
			prevs = new HashSet<Task>();
			map.put(task, prevs);
		}
		//如果前置任务集合不为空，且包含前置节点
		if (prevs.contains(prev)) {
			throw new IllegalArgumentException();
		}
		//前置任务集合不为空，且不包含前置节点，则添加
		prevs.add(prev);
	}

	/**
	 * 添加顶点
	 * @param task 任务顶点
	 */
	public void addTask(Task task) {
		if (tasks.contains(task)) {
			throw new IllegalArgumentException();
		}
		tasks.add(task);
	}

	public void remove(Task task) {
		if (!tasks.contains(task)) {
			return;
		}
		if (map.containsKey(task)) {
			map.remove(task);
		}
		for (Set<Task> set : map.values()) {
			if (set.contains(task)) {
				set.remove(task);
			}
		}
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Map<Task, Set<Task>> getMap() {
		return map;
	}

	public void setMap(Map<Task, Set<Task>> map) {
		this.map = map;
	}
}

