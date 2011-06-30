package org.apache.hadoop.mapred;

import java.util.*;

public class TrackerTasks {
	private int virtualLoad;
	private List<TaskInProgress> assignedTasks;
	private String taskTrackerName;
	
	public TrackerTasks(String taskTrackerName, 
						List<TaskInProgress> assignedTasks,
						int virtualLoad) {
		this.virtualLoad = virtualLoad;
		this.assignedTasks = assignedTasks;
		this.taskTrackerName = taskTrackerName;
	}
	
	public void updateTasks(int cost, TaskInProgress newTask) {
		this.virtualLoad += cost;
		this.assignedTasks.add(newTask);
	}
	
	public void setVirtualLoad(int newVL) {
		this.virtualLoad = newVL;
	}
	
	public void setTasks(List<TaskInProgress> tasks) {
		this.assignedTasks = tasks;
	}
	
	public void clearTasks() {
		this.assignedTasks.clear();
	}
	
	public int getVirtualLoad() {
		return this.virtualLoad;
	}
	
	public List<TaskInProgress> getTasks() {
		return this.assignedTasks;
	}
	
	public String getTrackerName() {
		return this.taskTrackerName;
	}
	
	public String toString() {
		return "Name: " + this.taskTrackerName + 
			"VL: " + this.virtualLoad + "tasks: " + this.assignedTasks;
	}
}
