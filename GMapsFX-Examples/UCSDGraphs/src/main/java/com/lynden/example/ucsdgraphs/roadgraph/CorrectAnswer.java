package com.lynden.example.ucsdgraphs.roadgraph;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CorrectAnswer {
    public int vertices;
    public int edges;
    public List<GeographicPoint> path;
    public CorrectAnswer(String file, boolean hasEdges) {
        try {
            String classPathPrefix = "/";
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    getClass().getResourceAsStream(
                        classPathPrefix + file
                    )
                )
            );

            if (hasEdges) {
                vertices = Integer.parseInt(reader.readLine());
                edges = Integer.parseInt(reader.readLine());
            }
            path = new ArrayList<>();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                String[] pieces = nextLine.split(" ");
                if (pieces.length == 2) {
                    double x = Double.valueOf(pieces[0]);
                    double y = Double.valueOf(pieces[1]);
                    path.add(new GeographicPoint(x, y));
                }
            }

            if (path.size() == 0)
                path = null;

        } catch (Exception e) {
            System.err.println("Error reading correct answer!");
            e.printStackTrace();
        }
    }
}
