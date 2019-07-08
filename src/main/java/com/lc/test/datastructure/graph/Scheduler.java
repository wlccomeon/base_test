package com.lc.test.datastructure.graph;

import java.util.*;

/**
 * @Description: 调度器的实现比较简单，就是遍历任务集合，
 * 				找出待执行的任务集合，放到一个List中，再串行执行（若考虑性能，可优化为并行执行）。
 * 				若List为空，说明所有任务都已执行，则这一次任务调度结束。
 * @author: wlc
 * @date: 2019/7/4 0004 7:41
 **/
public class Scheduler {
	//自顶向下执行
	// 4  3
	//      5
	//   2    6
	// 6   1
	//
	//
	//
	//
	public void schedule(Digraph digraph) {
		while (true) {
			List<Task> todo = new ArrayList<Task>();
			//从图中取出待执行的任务
			for (Task task : digraph.getTasks()) {
				if (!task.hasExecuted()) {
					Set<Task> prevs = digraph.getMap().get(task);
					if (prevs != null && !prevs.isEmpty()) {
						boolean toAdd = true;
						for (Task task1 : prevs) {
							//确保前置任务均已执行
							if (!task1.hasExecuted()) {
								toAdd = false;
								break;
							}
						}
						if (toAdd) {
							todo.add(task);
						}
					} else {
						todo.add(task);
					}
				}
			}
			//若有待执行任务，则循环执行；没有待执行任务，则结束循环。
			if (!todo.isEmpty()) {
				for (Task task : todo) {
					if (!task.execute()) {
						throw new RuntimeException();
					}
				}
			} else {
				break;
			}
		}
	}

	/**
	 * 自底向上运行任务
	 * @param digraph
	 */
	public void convertSchedule(Digraph digraph) {
		//第一次取出所有叶子节点，并执行叶子结点
		//第2及第N次取出所有已经执行过的叶子节点的上一级
		while (true) {
			List<Task> todo = new ArrayList<Task>();
			for (Task task : digraph.getTasks()) {
				//没有执行过的节点
				if (!task.hasExecuted()) {
					Set<Task> prevs = digraph.getMap().get(task);
					//有子节点
					if (prevs != null && !prevs.isEmpty()) {
						boolean toAdd = true;
						for (Task task1 : prevs) {
							//有子节点未执行，则中断循环，该节点不加入本次执行任务
							if (!task1.hasExecuted()) {
								toAdd = false;
								break;
							}
						}
						if (toAdd) {
							todo.add(task);
						}
					} else {
						todo.add(task);
					}
				}
			}
			//若有待执行任务，则循环执行；没有待执行任务，则结束循环。
			if (!todo.isEmpty()) {

				//执行深度最大的几个子节点，其余深度比较小的剔除此次执行

				//对list根据depth进行倒序排列
				Comparator<Task> taskComparator = Comparator.comparing(Task::getDepth);
				Collections.sort(todo,taskComparator.reversed());

				List<Task> targetTasks = new ArrayList<>();
				//选出最大深度的几个
				int size = todo.size();
				//集合中数据的深度是否全部相同的标记
				boolean depthAllEqual = true;
				Task firstTask = todo.get(0);
				//判断集合中元素的深度是否全部相等
				for (int i = 0; i < size; i++) {
					Task idxTask = todo.get(i);
					if (firstTask.getDepth()!=idxTask.getDepth()){
						depthAllEqual = false;
						//有不等的，则获取不等数据之前的数据。
						targetTasks = todo.subList(0,i);
						break;
					}
				}
				//若全部相等，则直接获取todoList。
				if (depthAllEqual){
					targetTasks = todo;
				}
				//遍历执行选出的节点
				for (Task task : targetTasks) {
					if (!task.execute()) {
						throw new RuntimeException();
					}
				}
				//该批次执行完毕，为下个批次传递参数
				System.out.println("深度为"+targetTasks.get(0).getDepth()+"的批次执行完毕");
			} else {
				break;
			}
		}
	}

	public static void main(String[] args) {
		Digraph digraph = new Digraph();
		//构建顶点
		Task task1 = new Task(1L, "task1", 0);
		Task task2 = new Task(2L, "task2", 0);
		Task task3 = new Task(3L, "task3", 0);
		Task task4 = new Task(4L, "task4", 0);
		Task task5 = new Task(5L, "task5", 0);
		Task task6 = new Task(6L, "task6", 0);
		Task task7 = new Task(7L, "task7", 0);
		Task task8 = new Task(8L, "task8", 0);

		task1.setDepth(1);
		task2.setDepth(3);
		task3.setDepth(4);
		task4.setDepth(4);
		task5.setDepth(3);
		task6.setDepth(2);
		task7.setDepth(3);
		task8.setDepth(4);

		digraph.addTask(task1);
		digraph.addTask(task2);
		digraph.addTask(task3);
		digraph.addTask(task4);
		digraph.addTask(task5);
		digraph.addTask(task6);
		digraph.addTask(task7);
		digraph.addTask(task8);

		//构建边
		digraph.addEdge(task1, task6);
		digraph.addEdge(task6, task7);
		digraph.addEdge(task6, task5);
		digraph.addEdge(task6, task2);
		digraph.addEdge(task2, task3);
		digraph.addEdge(task2, task4);
		digraph.addEdge(task5,task8);

		//执行任务
		Scheduler scheduler = new Scheduler();
		scheduler.convertSchedule(digraph);

	}
}

