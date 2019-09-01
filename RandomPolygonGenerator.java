package com.havelsan.visgraph.sampler;

import java.util.ArrayList;
import java.util.List;

public class RandomPolygonGenerator {


    private static int MAX_X_COORDINATE = 800;
    private static int MIN_X_COORDINATE = 12;

    private static int MAX_Y_COORDINATE = 600;
    private static int MIN_Y_COORDINATE = 12;


    public static void generateRandomPolygons(int numPolygons , List<XPolygon> polygonList ){
        Integer[] vertsPerPoly = new Integer[numPolygons];
        List<Point[]> segments = new ArrayList<>();
        PolygonUtils.assingRandomVertx(numPolygons, vertsPerPoly);
        for (int i = 0; i < numPolygons; i++) {
            int vertCount = vertsPerPoly[i];
            int boundX = RandomUtil.getRandomInterval(MIN_X_COORDINATE, MAX_X_COORDINATE);
            int boundY = RandomUtil.getRandomInterval(MIN_Y_COORDINATE, MAX_Y_COORDINATE);
            XPolygon createdRandomOne = PolygonUtils.createPolygon(vertCount, boundX, boundY);
            boolean intersect = PolygonUtils.polygonIntersect(segments, createdRandomOne);
            if (intersect == false) {
                polygonList.add(createdRandomOne);
                PolygonUtils.createSegments(segments, createdRandomOne);
            }
        }
    }

    public static void generateSegments(List<Point[]> segments,List<XPolygon> polygonList){
        for (XPolygon polygon : polygonList) {
                PolygonUtils.createSegments(segments, polygon);
        }
    }

    public static void generateVertices(List<Point> vertices,List<XPolygon> polygonList){
        for (XPolygon polygon : polygonList) {
            List<Point> points = polygon.getPoints();
            for (Point point : points) {
                vertices.add(point);
            }
        }
    }

}
