package com.lynden.example.ucsdgraphs.roadgraph;

/**
 * Representation of a Road on the map, used as an Edge in Graph data structure.
 * In this class, type parameter <T> is used instead of underlying GeographicPoint class.
 * We can reuse this Road class for dealing Graphs whose vertices are not GeographicPoint.
 * 
 * @author Nay Lwin
 *
 * @param <T>
 */
public class Road<T> implements Comparable<Road<T>> {
	private T from;
	private T to;
	private String roadName;
	private String roadType;
	private double length;

	public Road(T from, T to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Road(T from, T to, String roadName, String roadType, double length) {
		this(from, to);
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	public T getFrom() {
		return from;
	}

	public T getTo() {
		return to;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "Road [to=" + to + ", roadName=" + roadName + ", roadType=" + roadType + ", length=" + length + "]";
	}

	@Override
	public int compareTo(Road<T> o) {
		
		if (this.length > o.length)
			return 1;
		else if (this.length < o.length)
			return -1;
		
		return 0;
	}

}
