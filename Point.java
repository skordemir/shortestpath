package com.havelsan.visgraph.sampler;

import java.util.*;

/**
 * @author skordemir
 *
 */
public class Point {

	private double x;
	private double y;
	private List<Point> visiblePoints;
	private Double distance = Double.MAX_VALUE;
	private List<Point> shortestPath = new LinkedList<>();

	Map<Point, Double> adjacentNodes = new HashMap<>();

	public HashMap<Point , Double> distanceMap;

	public void addDestination(Point destination, Double distance) {
		adjacentNodes.put(destination, distance);
	}

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		visiblePoints = new ArrayList<Point>();
		distanceMap = new HashMap<Point, Double>();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void addVisiblePoint(Point p) {
		if (!visiblePoints.contains(p))
			visiblePoints.add(p);
	}

	public List<Point> getVisiblePoints() {
		return this.visiblePoints;
	}

	@Override
	public boolean equals(Object arg0) {
		Point tmp = (Point) arg0;
		if (tmp.getX() == this.getX() && tmp.getY() == this.getY())
			return true;
		return false;
	}

	public HashMap<Point, Double> getDistanceMap() {
		return distanceMap;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<Point> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Point> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Map<Point, Double> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Point, Double> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}
}
