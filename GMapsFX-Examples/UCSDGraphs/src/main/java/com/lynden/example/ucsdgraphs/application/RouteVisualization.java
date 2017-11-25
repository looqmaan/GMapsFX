/** Class to aid with route visualization for search
 *
 * @author UCSD MOOC development team
 *
 */
package com.lynden.example.ucsdgraphs.application;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;
import com.lynden.gmapsfx.javascript.IJavascriptRuntime;
import com.lynden.gmapsfx.javascript.JavascriptArray;
import com.lynden.gmapsfx.javascript.JavascriptRuntime;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RouteVisualization {
    List<GeographicPoint> points;
    ArrayList<Marker> markerList;
    MarkerManager manager;
    JavascriptArray markers;
    IJavascriptRuntime runtime;


    public RouteVisualization(MarkerManager manager) {
        points = new ArrayList<>();
        markerList = new ArrayList<>();
        this.manager = manager;
    }

    public void acceptPoint(GeographicPoint point) {
        points.add(point);

        // System.out.println("accepted point : " + point);
    }


    public void startVisualization() {

        LatLongBounds bounds = new LatLongBounds();
        List<LatLong> latLongs = new ArrayList<LatLong>();
        JavascriptArray jsArray = new JavascriptArray();
        manager.hideIntermediateMarkers();
        manager.hideDestinationMarker();
//    	manager.disableRouteButtons(true);

        // create JavascriptArray of points
        for(GeographicPoint point : points) {
            LatLong ll = new LatLong(point.getX(), point.getY());
            MarkerOptions options = MarkerManager.createDefaultOptions(ll);
            Marker newMarker = new Marker(options);
            jsArray.push(newMarker);
            markerList.add(newMarker);
            bounds.extend(ll);
        }

        // fit map bounds to visualization
        manager.getMap().fitBounds(bounds);

        // get javascript runtime and execute animation
        runtime = JavascriptRuntime.getInstance();
        try {
            String command = runtime.getFunction("visualizeSearch", manager.getMap(), jsArray);

            runtime.execute(command);
        } catch (Exception e) {
            System.err.println("======= Error occurred in RouteVisualization =======");
            e.printStackTrace();
        }
        // System.out.println(command);

//    	MapApp.showInfoAlert("Nodes visited :"  , latLongs.size() +" nodes were visited in the search");
        manager.disableVisButton(true);
//        manager.disableRouteButtons(false);

    }

    public void clearMarkers() {
        for(Marker marker : markerList) {
            marker.setVisible(false);
        }
    }

}