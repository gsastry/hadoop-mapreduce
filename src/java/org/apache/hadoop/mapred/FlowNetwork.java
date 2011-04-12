package org.apache.hadoop.mapred;

import java.util.*;
import java.util.Map;
import java.util.HashMap;

public class FlowNetwork {
	class Edge {
		private String source;
		private String sink;
		private int capacity;
		
		Edge (String source, String sink, int capacity) {
			this.setSource(source);
			this.setSink(sink);
			this.setCapacity(capacity);
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		public int getCapacity() {
			return capacity;
		}

		public void setSink(String sink) {
			this.sink = sink;
		}

		public String getSink() {
			return sink;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getSource() {
			return source;
		}
	}
	
	// adjacency matrix
	// Vertex --> List of Edges mapping
	private Map<String,List<Edge>> adjMatrix = 
		new HashMap<String,List<Edge>>();
	// flow matrix
	// Edge --> Integer flow on that edge mapping
	private Map<Edge, Integer> flowMatrix = 
		new HashMap<Edge,Integer>();
	
	public FlowNetwork() {
		
	}
	
	public void addVertex(String vertex) {
		adjMatrix.put(vertex, new ArrayList<Edge>());
	}
	
	public void getEdges(String u, String v, int w) {
		Edge fwdEdge = new Edge(u, v, w);
		Edge revEdge = new Edge(v, u, 0);
	}

}
