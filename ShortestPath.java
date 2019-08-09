package com.havelsan.visgraph.sampler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skordemir
 *
 */
public class ShortestPath {

	// create random polygons

	Point startingPoint;

	private int numPolygons = 800;
	private int maxCollisions = 5;
	private int polygonMaxVerts = 5;
	private int boundLimit;
	private Integer[] vertsPerPoly;
	private Integer vertCount = 0;

	private static int MAX_X_COORDINATE = 800;
	private static int MIN_X_COORDINATE = 12;

	private static int MAX_Y_COORDINATE = 600;
	private static int MIN_Y_COORDINATE = 12;
	public List<XPolygon> polygonList = new ArrayList<XPolygon>();

	private List<Point[]> segments = new ArrayList<Point[]>();

	public void getRandomPolygons(Point startingPoint , Point endPoint) {
		vertsPerPoly = new Integer[numPolygons];
		PolygonUtils.assingRandomVertx(numPolygons, vertsPerPoly, vertCount);
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
		XPolygon pl = new XPolygon();
		pl.getPoints().add(startingPoint);
		polygonList.add(pl);
		List<Point> vertices = new ArrayList<>();
		for (XPolygon polygon : polygonList) {
			List<Point> points = polygon.getPoints();
			for (Point point : points) {
				vertices.add(point);
			}
		}
		vertices.add(endPoint);
		PolygonUtils.craeteVisibilityGraphs(segments, vertices);
		
		
		
		System.out.print("here");
	}
	
	public void djikstra(List<Point> vertices){
		
	}

	public static void main(String[] args) {
		ShortestPath path = new ShortestPath();
		Point startingPoint = new Point(23, 23);
		Point endPoint = new Point(129, 487);
		path.getRandomPolygons(startingPoint , endPoint);
		for (XPolygon polygon : path.polygonList) {
			List<Point> points = polygon.getPoints();
			for (Point point : points) {
				System.out.print(point.getX() + ":" + point.getY() + "-");
			}
			System.out.println();

		}
	}

}
