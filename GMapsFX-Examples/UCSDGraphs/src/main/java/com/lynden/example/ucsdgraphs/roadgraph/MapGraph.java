/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package com.lynden.example.ucsdgraphs.roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;
import com.lynden.example.ucsdgraphs.util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {

	private int numVertices;
	private int numEdges;

	protected Map<MapNode<GeographicPoint>, List<Road<GeographicPoint>>> adjListMap;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		numVertices = 0;
		numEdges = 0;
		adjListMap = new HashMap<>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return numVertices;
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		Set<GeographicPoint> vertices = new HashSet<>(adjListMap.size());
		for (MapNode<GeographicPoint> node : adjListMap.keySet()) {
			vertices.add(node.getLocation());
		}
		return vertices;
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return numEdges;
	}

	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location
	 *            The location of the intersection
	 * @return true if a node was added, false if it was not (the node was already
	 *         in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		MapNode<GeographicPoint> node = new MapNode<>(location);
		if (location == null || adjListMap.containsKey(node))
			return false;

		List<Road<GeographicPoint>> neighbors = new ArrayList<>();
		adjListMap.put(node, neighbors);
		numVertices++;

		return true;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from
	 *            The starting point of the edge
	 * @param to
	 *            The ending point of the edge
	 * @param roadName
	 *            The name of the road
	 * @param roadType
	 *            The type of the road
	 * @param length
	 *            The length of the road, in km
	 * @throws IllegalArgumentException
	 *             If the points have not already been added as nodes to the graph,
	 *             if any of the arguments is null, or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		if (from == null || to == null || length < 0.0D) {
			throw new IllegalArgumentException("Null objects or invalid length provided.");
		}

		if (!nodeExists(to) || !nodeExists(from)) {
			throw new IllegalArgumentException("Invalid vertex provided.");
		}

		Road<GeographicPoint> edge = new Road<>(from, to);
		edge.setLength(length);
		edge.setRoadName(roadName);
		edge.setRoadType(roadType);

		MapNode<GeographicPoint> node = new MapNode<>(from);
		node.addEdge(edge);
		(adjListMap.get(node)).add(edge);
		numEdges++;
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how to
	 *            use it.
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		final Optional<List<GeographicPoint>> path = (new BreadthFirstSearch(this)).setNodeAccepter(nodeSearched)
				.searchPath(start, goal);

		return path.isPresent() ? path.get() : null;
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how to
	 *            use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {

		// Debugging for Quiz
		// System.out.println("== dijkstra ==");
		final Optional<List<GeographicPoint>> path = (new Dijkstra(this)).setNodeAccepter(nodeSearched)
				.searchPath(start, goal);

		return path.isPresent() ? path.get() : null;
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start
	 *            The starting location
	 * @param goal
	 *            The goal location
	 * @param nodeSearched
	 *            A hook for visualization. See assignment instructions for how to
	 *            use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {

		// Debugging for Quiz
		// System.out.println("== A* ==");
		
		final Optional<List<GeographicPoint>> path = (new AStar(this)).setNodeAccepter(nodeSearched)
				.searchPath(start, goal);

		return path.isPresent() ? path.get() : null;
	}

	/**
	 * Find the neighbors vertices for the given geographic point.
	 * 
	 * @param geographicPoint
	 * @return The list of road object containing both vertices and label.
	 */
	protected List<Road<GeographicPoint>> getNeighbors(GeographicPoint geographicPoint) {
		List<Road<GeographicPoint>> neighbors = new ArrayList<>();

		MapNode<GeographicPoint> node = new MapNode<>(geographicPoint);
		adjListMap.get(node).forEach(neighbors::add);

		return neighbors;
	}

	/**
	 * Loop through the vertices in the graph, if specified geographic point matches
	 * the vertex's location, return the vertex (an instance of MapNode).
	 * 
	 * @param geographicPoint
	 * @return A single MapNode from graph for the specified geographic point.
	 */
	protected MapNode<GeographicPoint> getMapNode(GeographicPoint geographicPoint) {
		Iterator<Entry<MapNode<GeographicPoint>, List<Road<GeographicPoint>>>> iterator = adjListMap.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			MapNode<GeographicPoint> key = (MapNode<GeographicPoint>) iterator.next().getKey();
			if (key.getLocation().equals(geographicPoint))
				return key;

		}

		return null;
	}

	/**
	 * Loop through the graph to search for specified geographic point.
	 *
	 * @param geographicPoint
	 *            A geographic point indicating vertex in the graph.
	 * @return boolean indicating whether specified geographic point exists in the
	 *         graph.
	 */
	private boolean nodeExists(GeographicPoint geographicPoint) {
		MapNode<GeographicPoint> node = getMapNode(geographicPoint);
		return node != null;
	}

	public static void main(String[] args) {
//		MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");

		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		
		Consumer<GeographicPoint> nodeSearched = (x) -> {};
		final GeographicPoint start = new GeographicPoint(1.0, 1.0);
		final GeographicPoint goal = new GeographicPoint(8.0, -1.0);
		final Optional<List<GeographicPoint>> path = (new AStar(firstMap))
				.setNodeAccepter(nodeSearched)
				.searchPath(start, goal);

		System.out.println("");
		
		if (path.isPresent()) 
			System.out.println(path.get());
		else
			System.out.println("No path found!");
		
		System.out.println(firstMap);
		
		System.out.println("DONE.");
	}

	@Override
	public String toString() {
		String adjListMapStr = "";
		
		Iterator<Entry<MapNode<GeographicPoint>, List<Road<GeographicPoint>>>> iterator = adjListMap.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			MapNode<GeographicPoint> key = (MapNode<GeographicPoint>) iterator.next().getKey();
			
			String edgeStr = "";
			
			for (Road<GeographicPoint> edge : adjListMap.get(key)) {
				edgeStr += "\n\tLoc: " + edge.getTo() + 
						", street: " + edge.getRoadName() + 
					", length: " + edge.getLength();
			}
			
			adjListMapStr += "\n\n vertex: " + key;
			adjListMapStr += "\n neighbors: " + edgeStr; 
		}
		
		String str = "MapGraph [numVertices=" + numVertices + ", numEdges=" + numEdges + ", adjListMap=" + adjListMapStr + "]";
		return str;
	}

	
	
}
