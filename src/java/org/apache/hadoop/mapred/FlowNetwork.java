package org.apache.hadoop.mapred;

import java.util.*;
import java.io.*;

public class FlowNetwork {
	public static final int WHITE = 0, GRAY = 1, BLACK = 2;
	private int[][] flow, capacity, res_capacity;
	private int[] parent, color, queue, min_capacity;
	private int size, source, sink, first, last, max_flow;
	
	public FlowNetwork(int numVertices, 
					   int[][] capacityMatrix,
					   int sourceIndex,
					   int sinkIndex,
					   int[][] flowMatrix,
					   int maxFlow) {
		this.size = numVertices;
		this.capacity = capacityMatrix;
		this.source = sourceIndex;
		this.sink = sinkIndex;
		this.max_flow = maxFlow;
		this.flow = flowMatrix;
		maxFlow();
	}
	
	private void maxFlow() {
		// flow = new int[size][size];
		res_capacity = new int[size][size];
		parent = new int[size];
		min_capacity = new int[size];
		color = new int[size];
		queue = new int[size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				res_capacity[i][j] = capacity[i][j];
			}
		}
		
		while (BFS(source)) {
			System.out.println("max_flow: " + max_flow );
			System.out.println("min_capacity[sink]: " + min_capacity[sink]);
			max_flow += min_capacity[sink];
			System.out.println(max_flow);
			int v = sink, u;
			while (v != source) {
				u = parent[v];
				flow[u][v] += min_capacity[sink];
				flow[v][u] -= min_capacity[sink];
				res_capacity[u][v] -= min_capacity[sink];
				res_capacity[v][u] += min_capacity[sink];
				v = u;
			}
		}
	}
	
	private boolean BFS(int source) {
		for (int i = 0; i < size; i++) {
			color[i] = WHITE;
			min_capacity[i] = Integer.MAX_VALUE;
		}
		
		first = last = 0;
		queue[last++] = source;
		color[source] = GRAY;
		
		// while queue not empty
		while (first != last) { 
			int v = queue[first++];
			for (int u = 0; u < size; u++) {
				if (color[u] == WHITE && res_capacity[v][u] > 0) {
					min_capacity[u] = Math.min(min_capacity[v], res_capacity[v][u]);
					System.out.println("min_capacity[" + v+"]:"+ min_capacity[v]);
					System.out.println("res_capacity[v][u]: " + res_capacity[v][u]);
					System.out.println("min_capacity[" + u+"]:"+ min_capacity[u]);
					parent[u] = v;
					color[u] = GRAY;
					if (u == sink) {
						return true;
					}
					queue[last++] = u;
				}
			}
		}
		return false;
	}
	
	public int[][] getFlowMatrix() {
		return this.flow;
	}
	
	public int getMaxFlow() {
		return this.max_flow;
	}
	
	// converts a flow to a augmenting capacity input for the next run of maxFlow
	public int[][] flowToCapacityInput(int[][]flow, int[][]capacity, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (flow[i][j] != 0) {
					capacity[i][j] -= flow[i][j];
					capacity[j][i] += flow[i][j];
				}
			}
		}
		return capacity;
	}
	
	// converts a partial assignment to a flow
	// partial is a map from index of server in servers[]-> index of task in tasks[]
	public int[][] partialToFlow(Map<Integer,List<Integer>> partial,
								 int numServers,
								 int numTasks) {
		int size = numServers + numTasks + 2;
		int[][] flow = new int[size][size];
		int source = 0;
		int sink = 1;
		int offset = 2;
		
		Iterator<Map.Entry<Integer,List<Integer>>> it 
			= partial.entrySet().iterator();
		int i = 0;
		
		while (it.hasNext()) {
			List<Integer> assignedTasks = partial.get(i);
			if (assignedTasks != null) {
				for (int taskIndex : assignedTasks) {
					int task = taskIndex + offset;
					int server = task + i + 1;
					flow[source][task] = 1;
					flow[task][server] = 1;
					flow[server][sink] = 1;
				}
			}
			i++;
		}
		
		return flow;
	}
	
	public Map<Integer, List<Integer>> flowToPartial(int[][] flow,
													 int numTasks,
													 int numServers) {
		Map<Integer,List<Integer>> partial = 
			new HashMap<Integer,List<Integer>>();
		int numVertices = numTasks + numServers;
		int offset = 2;
		int source = 0;
		int sink = 1;
		
		/* initialize partial assignment with indices of servers */
		for (int i = 0; i < numServers; i++) {
			List<Integer> listTaskIndices = new ArrayList<Integer>();
			partial.put(i, listTaskIndices);
		}
		
		/* if there is a source--task--server--sink flow of 1, then assign
		 * task to server
		 */
		for (int i = offset; i < numTasks + offset; i++) {
			if (flow[source][i] == 1) {
				for (int j = numTasks + offset; j < numVertices; j++) {
					if (flow[i][j] == 1 && flow[j][sink] == 1) {
						int server = j - (numTasks + offset);
						int task = i - offset;
						List<Integer> assignedTasks = partial.get(server);
						
						assignedTasks.add(task);
						partial.put(server, assignedTasks);
						
						break ;
					}
				}
			}
		}
		return partial;
	}
}
