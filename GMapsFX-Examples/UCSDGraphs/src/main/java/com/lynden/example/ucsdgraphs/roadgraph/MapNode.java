/**
 * @author UCSD MOOC development team
 * Class representing a vertex (or node) in our MapGraph
 *
 */
package com.lynden.example.ucsdgraphs.roadgraph;

import java.util.HashSet;
import java.util.Set;

class MapNode<T> implements Comparable<MapNode<T>> {
	/** The list of edges out of this node */
	private HashSet<Road<T>> edges;

	/** the latitude and longitude of this node */
	private T location;

	/** the distance from starting point to this node in the graph */
	private Double distance = 0.0D;

	/** the predicted (straight line) distance from this node to goal */
	private Double predictedDistance = 0.0D;

	/**
	 * Create a new MapNode at a given Geographic location
	 * 
	 * @param loc the location of this node
	 */
	MapNode(T loc) {
		location = loc;
		edges = new HashSet<>();
	}

	/**
	 * @return distance from starting point to this node in graph.
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	/**
	 * Add an edge that is outgoing from this node in the graph
	 * 
	 * @param edge
	 *            The edge to be added
	 */
	void addEdge(Road<T> edge) {
		edges.add(edge);
	}

	/**
	 * Return the neighbors of this MapNode
	 * 
	 * @return a set containing all the neighbors of this node
	 */
	Set<T> getNeighbors() {
		Set<T> neighbors = new HashSet<>();
		for (Road<T> edge : edges) {
			neighbors.add(edge.getTo());
		}
		return neighbors;
	}

	/**
	 * Get the geographic location that this node represents
	 * 
	 * @return the geographic location of this node
	 */
	T getLocation() {
		return location;
	}

	/**
	 * return the edges out of this node
	 * 
	 * @return a set containing all the edges out of this node.
	 */
	Set<Road<T>> getEdges() {
		return edges;
	}
	
	/**
	 * @return return predicted distance
	 */
	public Double getPredictedDistance() {
		return predictedDistance;
	}

	/**
	 * Set predicted distance
	 * 
	 * @param predictedDistance
	 */
	public void setPredictedDistance(Double predictedDistance) {
		this.predictedDistance = predictedDistance;
	}

	/**
	 * Returns whether two nodes are equal. Nodes are considered equal if their
	 * locations are the same, even if their street list is different.
	 * 
	 * @param o
	 *            the node to compare to
	 * @return true if these nodes are at the same location, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		MapNode<T> node = (MapNode<T>) o;
		return node.location.equals(this.location);
	}

	/**
	 * Because we compare nodes using their location, we also may use their location
	 * for HashCode.
	 * 
	 * @return The HashCode for this node, which is the HashCode for the underlying
	 *         point
	 */
	@Override
	public int hashCode() {
		return location.hashCode();
	}

	/**
	 * ToString to print out a MapNode object
	 * 
	 * @return the string representation of a MapNode
	 */
	@Override
	public String toString() {
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " distance: " + distance + ", ";
		toReturn += " intersects streets: ";
		for (Road<T> e : edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}

	// For debugging, output roadNames as a String.
	public String roadNamesAsString() {
		String toReturn = "(";
		for (Road<T> e : edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}

	@Override
	public int compareTo(MapNode<T> o) {
		Double d1 = distance + predictedDistance;
		Double d2 = o.distance + o.predictedDistance;
		return d1.compareTo(d2);
	}

}