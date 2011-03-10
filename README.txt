FlowScheduler: A New Task Scheduling Algorithm for Hadoop/MapReduce
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

by: Girish Sastry

FlowScheduler is a new task scheduler for hadoop, optimal within an
additive constant. It consists primarily of two phases:

1) max-cover: a flow augmenting algorithm based of Fold-Fulkerson
2) bal-assign: a greedy task assignment algorithm that exploits data locality
    and network latency

