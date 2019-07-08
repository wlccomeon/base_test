package com.lc.test.datastructure.graph;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/7/4 0004 7:40
 **/
public class Task implements Executor{
	/**任务id*/
	private Long id;
	/**任务名*/
	private String name;
	/**任务状态，简化为0：未执行，1：已执行*/
	private int state;
	/**是否为根节点*/
	private boolean isRoot;
	/**节点深度*/
	private int depth;

	public Task(Long id, String name, int state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}

	@Override
	public boolean execute() {
		System.out.println("Task id: [" + id + "], " + "task name: [" + name +"] is running");
		state = 1;
		return true;
	}
	/**返回任务是否已执行。*/
	public boolean hasExecuted() {
		return state == 1;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean root) {
		isRoot = root;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static void main(String[] args) {
		List<Task> mylist = new ArrayList<>(4);
		Task task1 = new Task(1L, "task1", 0);
		Task task2 = new Task(2L, "task2", 0);
		Task task3 = new Task(3L, "task3", 0);
		Task task4 = new Task(4L, "task4", 0);
		Task task5 = new Task(5L, "task5", 0);
		Task task6 = new Task(6L, "task6", 0);
		task1.setDepth(1);
		task2.setDepth(3);
		task3.setDepth(4);
		task4.setDepth(4);
		task5.setDepth(3);
		task6.setDepth(2);
		mylist.add(task1);
		mylist.add(task2);
		mylist.add(task3);
		mylist.add(task4);
		mylist.add(task5);
		mylist.add(task6);

		//排序
//		Collections.sort(mylist, new Comparator<Task>() {
//			@Override
//			public int compare(Task o1, Task o2) {
//				int x1 = o1.getDepth();
//				int x2 = o2.getDepth();
//				return x1>x2?1:(x1==x2?0:-1);
//			}
//		});

		//对list根据depth进行倒序排列
		Comparator<Task> taskComparator = Comparator.comparing(Task::getDepth);
		Collections.sort(mylist,taskComparator.reversed());

		List<Task> targetTasks = new ArrayList<>();
		targetTasks = mylist.subList(0,2);
		//选出最大深度的几个
		for (int i = 0; i < mylist.size(); i++) {
			Task t1 = mylist.get(i);
			if (i+1==mylist.size()){
				break;
			}
			Task t2 = mylist.get(i+1);
			if (t1.getDepth()>t2.getDepth()){
				targetTasks = mylist.subList(0,i+1);
				break;
			}
		}


		System.out.println("targetList---->>>>"+JSON.toJSONString(targetTasks));
		System.out.println("mylist---->>>>"+JSON.toJSONString(mylist));

	}

}

