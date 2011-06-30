package org.apache.hadoop.mapred;

import java.util.*;

public class TrackerTasksComparator implements Comparator<TrackerTasks> {
	public int compare(TrackerTasks tt1, TrackerTasks tt2) {
		
		int tt1_vl = tt1.getVirtualLoad();
		int tt2_vl = tt2.getVirtualLoad();
		
		if (tt1_vl > tt2_vl) {
			return 1;
		}
		else if (tt1_vl < tt2_vl) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
