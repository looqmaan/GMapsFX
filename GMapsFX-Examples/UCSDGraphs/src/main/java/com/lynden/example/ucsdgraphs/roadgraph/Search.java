package com.lynden.example.ucsdgraphs.roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Abstract Search class provides common methods and utility methods to its
 * sub-classes. In this class, type parameter <T> is used instead of underlying
 * GeographicPoint class. We can reuse this Search class for dealing Graphs
 * whose vertices are not GeographicPoint.
 * 
 * @author Nay Lwin
 *
 * @param <T>
 */
abstract public class Search<T> {

	/**
	 * Reference to nodeAccepter (for Visualization).
	 */
	protected Consumer<T> nodeAccepter;

	/**
	 * Reference to original graph.
	 */
	protected MapGraph graph;

	protected Map<T, T> parentMap;
	protected Set<T> visited;

	abstract public Optional<List<T>> searchPath(T start, T goal);

	/**
	 * Utility method to reconstruct the path from vertices stored in parentMap.
	 * 
	 * @param start
	 * @param goal
	 * @param parentMap
	 * @return reconstructed path from parentMap.
	 */
	protected List<T> constructPath(T start, T goal, Map<T, T> parentMap) {
		final LinkedList<T> path = new LinkedList<>();
		T current = goal;
		
		while(!current.equals(start)) {
			path.addFirst(current);
			T next = parentMap.get(current);
			current = next;
		}
		
		path.addFirst(start);
		return path;
	}

	/**
	 * Initialize class member instance variables.
	 */
	protected void initialize() {
		visited = new HashSet<>();
		parentMap = new HashMap<>();
	}

	/**
	 * Mark given node as visited.
	 * 
	 * @param current
	 */
	protected void visit(T current) {
		visited.add(current);
	}

	/**
	 * Check whether given node has been visited.
	 * 
	 * @param v
	 * @return
	 */
	protected boolean isVisited(T v) {
		return visited.contains(v);
	}

	/**
	 * @return nodeAccepter which is set via MapGraph.bfs() method.
	 */
	public Consumer<T> getNodeAccepter() {
		return nodeAccepter;
	}

	/**
	 * @param nodeAccepter
	 * @return self-reference
	 */
	public Search<T> setNodeAccepter(Consumer<T> nodeAccepter) {
		this.nodeAccepter = nodeAccepter;
		return this;
	}
}
