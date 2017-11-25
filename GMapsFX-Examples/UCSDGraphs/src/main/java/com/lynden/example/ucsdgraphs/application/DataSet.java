/**
 * Class to wrap the graph, current .map file map, and
 * other pertinent information about the map data sets
 *
 * @author UCSD MOOC development team
 */
package com.lynden.example.ucsdgraphs.application;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;
import com.lynden.example.ucsdgraphs.geography.RoadSegment;
import com.lynden.example.ucsdgraphs.roadgraph.MapGraph;
import com.lynden.example.ucsdgraphs.util.GraphLoader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DataSet {
    String filePath;
    MapGraph graph;
    Set<GeographicPoint> intersections;
    private HashMap<GeographicPoint, HashSet<RoadSegment>> roads;
    boolean currentlyDisplayed;

    public DataSet(String path) {
        this.filePath = path;
        graph = null;
        roads = null;
        currentlyDisplayed = false;
    }

    public void setGraph(MapGraph graph) {
        this.graph = graph;
    }

    public void setRoads(HashMap<GeographicPoint, HashSet<RoadSegment>> roads) {
        this.roads = roads;
    }

    public MapGraph getGraph() {
        return graph;
    }

    /** Return the intersections in this graph.
     * In order to keep it consistent, if getVertices in the graph returns something
     * other than null (i.e. it's been implemented) we get the vertices from
     * the graph itself.  But if the graph hasn't been implemented, we return
     * the set of intersections we separately maintain specifically for this purpose.
     * @return The set of road intersections (vertices in the graph)
     */
    public Set<GeographicPoint> getIntersections() {
        Set<GeographicPoint> intersectionsFromGraph = graph.getVertices();
        if (intersectionsFromGraph == null) {
            return intersections;
        } else {
            return intersectionsFromGraph;
        }
    }

    public HashMap<GeographicPoint, HashSet<RoadSegment>> getRoads() {
        return this.roads;
    }

    public void initializeGraph() {
        graph = new MapGraph();
        roads = new HashMap<>();
        intersections = new HashSet<>();
        //TODO: change to use intersections for points in graph.
        GraphLoader.loadRoadMap(filePath, graph, roads, intersections);
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Object[] getPoints() {
        Set<GeographicPoint> pointSet = roads.keySet();
        return pointSet.toArray();
    }

    public boolean isDisplayed() {
        return this.currentlyDisplayed;
    }

    public void setDisplayed(boolean value) {
        this.currentlyDisplayed = value;
    }

}