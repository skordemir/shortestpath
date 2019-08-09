package com.havelsan.visgraph.sampler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skordemir
 *
 */
public class Point {

	private double x;
	private double y;
	private List<Point> visiblePoints;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		visiblePoints = new ArrayList<Point>();
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

}
