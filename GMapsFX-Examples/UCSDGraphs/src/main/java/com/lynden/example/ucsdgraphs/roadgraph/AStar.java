package com.lynden.example.ucsdgraphs.roadgraph;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;

/**
 * 
 * @author Nay Lwin
 *
 */
public class AStar extends Dijkstra {

	public AStar(MapGraph graph) {
		super(graph);
	}

	/**
	 * Heuristic method which provides straight line distance between two vertices in
	 * graph. The predicted distance is used as priority in PriorityQueue.
	 */
	@Override
	protected Double heuristic(GeographicPoint goal, GeographicPoint next) {
		return goal.distance(next);
	}

}
