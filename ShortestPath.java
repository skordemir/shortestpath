package com.havelsan.visgraph.sampler;

import java.util.*;

/**
 * @author skordemir
 *
 */
public class ShortestPath {

	// create random polygons

	Point startingPoint;

	private static int numPolygons = 800;
	private int maxCollisions = 5;
	private int polygonMaxVerts = 5;
	private Integer vertCount = 0;

	private static int MAX_X_COORDINATE = 800;
	private static int MIN_X_COORDINATE = 12;

	private static int MAX_Y_COORDINATE = 600;
	private static int MIN_Y_COORDINATE = 12;

	public List<XPolygon> getRandomPolygons(int numPolygons) {
		List<XPolygon> polygonList = new ArrayList<>();
		RandomPolygonGenerator.generateRandomPolygons(numPolygons,polygonList);
		return polygonList;
	}

	public List<Point> createVisiblityGraph(List<XPolygon> polygonList, Point startingPoint, Point endPoint) {
		List<Point> vertices = new ArrayList<>();
		List<Point[]> segments = new ArrayList<>();
		RandomPolygonGenerator.generateVertices(vertices , polygonList);
		vertices.add(startingPoint);
		vertices.add(endPoint);
		RandomPolygonGenerator.generateSegments(segments , polygonList);
		PolygonUtils.craeteVisibilityGraphs(segments, vertices);
		return vertices;
	}


	public static void main(String[] args) {
		ShortestPath path = new ShortestPath();
		Point startingPoint = new Point(23, 23);
		Point endPoint = new Point(129, 487);
		List<XPolygon> randomPolygons = path.getRandomPolygons(numPolygons);
		for (XPolygon polygon :randomPolygons) {
			List<Point> points = polygon.getPoints();
			for (Point point : points) {
				System.out.print(point.getX() + ":" + point.getY() + "-");
			}
			System.out.println();

		}
		Graph g = new Graph();
		List<Point> visiblityGraphs = path.createVisiblityGraph(randomPolygons,startingPoint , endPoint  );
		for (Point a:visiblityGraphs ) {
			List<Point> visiblePoints = a.getVisiblePoints();
			for (Point b:visiblePoints ) {
				double calcualteDistanceOf2points = calcualteDistanceOf2points(a, b);
				a.addDestination(b , calcualteDistanceOf2points);
			}
			g.addNode(a);
		}

		Graph graph = calculateShortestPathFromSource(g, startingPoint);
		PolygonGui gui = new PolygonGui();

		for (Point  point: visiblityGraphs ) {
			List<Point> visiblePoints = point.getVisiblePoints();
			System.out.println(point +":" +visiblePoints.size());

		}
	}

	public static double calcualteDistanceOf2points(Point a , Point b){
		double aX = a.getX();
		double bX = b.getX();
		double aY = a.getY();
		double bY = b.getY();
		double powX = Math.pow(Math.abs(aX - bX), 2);
		double powY = Math.pow(Math.abs(aY-bY) ,2);
		double sqrt = Math.sqrt(powX + powY );
		return  sqrt;
	}

	private static Point getLowestDistanceNode(Set< Point > unsettledNodes) {
		Point lowestDistanceNode = null;
		Double lowestDistance = Double.MAX_VALUE;
		for (Point node: unsettledNodes) {
			Double nodeDistance = node.getDistance();
			if (nodeDistance < lowestDistance) {
				lowestDistance = nodeDistance;
				lowestDistanceNode = node;
			}
		}
		return lowestDistanceNode;
	}

	public static Graph calculateShortestPathFromSource(Graph graph, Point source) {
		source.setDistance(new Double(0));

		Set<Point> settledNodes = new HashSet<>();
		Set<Point> unsettledNodes = new HashSet<>();

		unsettledNodes.add(source);

		while (unsettledNodes.size() != 0) {
			Point currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Map.Entry< Point, Double> adjacencyPair:
					currentNode.getAdjacentNodes().entrySet()) {
				Point adjacentNode = adjacencyPair.getKey();
				Double edgeWeight = adjacencyPair.getValue();
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return graph;
	}

	private static void calculateMinimumDistance(Point evaluationNode,
												 Double edgeWeigh, Point sourceNode) {
		Double sourceDistance = sourceNode.getDistance();
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<Point> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}

}
