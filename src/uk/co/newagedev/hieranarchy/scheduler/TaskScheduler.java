package uk.co.newagedev.hieranarchy.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskScheduler {

	private static Map<Integer, Task> tasks = new HashMap<Integer, Task>();
	private static List<Integer> tasksToRemove = new ArrayList<Integer>();
	private static int nextId = 0;
	
	public static int runTask(Task task) {
		task.id = nextId++;
		tasks.put(task.id, task);
		return task.id;
	}
	
	public static int getNextId() {
		return nextId++;
	}
	
	public static void removeTask(int id) {
		tasksToRemove.add(id);
	}
	
	public static void update() {
		for (Task task : tasks.values()) {
			task.update();
		}
		for (int id : tasksToRemove) {
			tasks.remove(tasks.get(id));
		}
		
		tasksToRemove.clear();
	}
}
