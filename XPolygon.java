package com.havelsan.visgraph.sampler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skordemir
 *
 */
public class XPolygon {

	private List<Point> points;

	public XPolygon() {
		points = new ArrayList();
	}

	public void add(Point p) {
		points.add(p);
	}

	public boolean contains(Point p) {
		boolean b = points.contains(p);
		return b;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}
