package com.lynden.example.ucsdgraphs.roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Map.Entry;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;

/**
 * 
 * @author Nay Lwin
 *
 */
public class Dijkstra extends Search<GeographicPoint> {

	private static final Double INFINITY = Double.MAX_VALUE;

	private PriorityQueue<MapNode<GeographicPoint>> queue;

	private boolean found = false;

	public Dijkstra(MapGraph graph) {
		super();
		this.graph = graph;
		initialize();
	}

	@Override
	public Optional<List<GeographicPoint>> searchPath(GeographicPoint start, GeographicPoint goal) {

		if (dijkstra(start, goal)) {
			List<GeographicPoint> value = constructPath(start, goal, parentMap);
			return Optional.of(value);
		}
		
		return Optional.empty();
	}

	@Override
	protected void initialize() {
		parentMap = new HashMap<>();
		visited = new HashSet<>();
		found = false;
		queue = new PriorityQueue<>(graph.getNumVertices());
	}
	
	/**
	 * Provide hook for A* Search
	 * 
	 * @param goal geographic location of the goal
	 * @param next geographic location of the 
	 * @return
	 */
	protected Double heuristic(GeographicPoint goal, GeographicPoint next) {
		return 0.0D;
	}

	/**
	 * Implementation of Dijkstra's algorithm.
	 * 
	 * @param start
	 * @param goal
	 * @return True when shortest path is found in the graph, otherwise False.
	 */
	private boolean dijkstra(GeographicPoint start, GeographicPoint goal) {

		Iterator<Entry<MapNode<GeographicPoint>, List<Road<GeographicPoint>>>> iterator = 
			graph.adjListMap.entrySet()
				.iterator();

		// initialize all vertices with +INFINITY except the starting vertex.
		// For starting vertex, set 0 for distance, and add it to queue.
		while (iterator.hasNext()) {
			MapNode<GeographicPoint> vertex = iterator.next().getKey();
			if (vertex.getLocation().equals(start)) {
				vertex.setDistance(0.0D);
				queue.add(vertex);
			} else
				vertex.setDistance(INFINITY);
		}

		// Debugging for Quiz
		// int numPoped = 0;
		
		// Loop through each item in queue. Order by lowest value in distance.
		// @see MapNode.compareTo()
		while (!queue.isEmpty()) {
			MapNode<GeographicPoint> current = queue.remove();
			GeographicPoint currentLocation = current.getLocation();
			// Debugging for Quiz
			// System.out.println("#" + ++numPoped + " poped: " + current);

			// If current vertex is not visited, mark as visited. Otherwise ignored.
			if (!isVisited(currentLocation)) {
				visit(currentLocation);
				if (currentLocation.equals(goal)) {
					found = true;
					return found;
				}

				// Get neighboring edges from current vertex.
				PriorityQueue<Road<GeographicPoint>> neighbors = getNeighbors(current);
				while (!neighbors.isEmpty()) {
					Road<GeographicPoint> neighbor = neighbors.remove();
					GeographicPoint neighborLocation = neighbor.getTo();
					MapNode<GeographicPoint> neighborNode = graph.getMapNode(neighborLocation);
					Double neighborDistance = neighbor.getLength();
					if (isVisited(neighborLocation))
						continue;

					// Get distance from current to neighbor and
					// get predicted distance from neighbor to goal.
					Double distToNeighbor = dist(current, neighborDistance);
					Double predictedDistance = heuristic(goal, neighborLocation);
					if (distToNeighbor < neighborNode.getDistance()) {
						// Note: Only distance is used as priority in Dijkstra's algorithm.
						// 	Both the distance and predicted distance are used in the 
						//	sub-class: A* search.
						// 	@see MapNode.compareTo() and AStar.heuristic()
						neighborNode.setDistance(distToNeighbor);
						neighborNode.setPredictedDistance(predictedDistance);
						queue.add(neighborNode);
                        parentMap.put(neighborLocation, current.getLocation());
						getNodeAccepter().accept(neighborLocation);
					}
				}
			}
		}

		return false;
	}

	/**
	 * Calculate distance from x to y
	 * 
	 * @param x A vertex in the graph
	 * @param distance The weight of the edge(x,y)
	 * @return calculated distance
	 */
	private Double dist(MapNode<GeographicPoint> x, Double distance) {
		Double distanceToX = x.getDistance();
		return distanceToX + distance;
	}

	/**
	 * This method return neighboring edges for specified vertex.
	 * Convert the List to PriorityQueue so that the edge with shortest
	 * distance are visited first.
	 * 
	 * @param node representing a vertex in graph
	 * @return Neighboring edges
	 */
	private PriorityQueue<Road<GeographicPoint>> getNeighbors(MapNode<GeographicPoint> node) {
		PriorityQueue<Road<GeographicPoint>> neighbors = new PriorityQueue<>();
		List<Road<GeographicPoint>> edges = graph.getNeighbors(node.getLocation());

		if (!edges.isEmpty())
			edges.forEach(neighbors::add);

		return neighbors;
	}

}
