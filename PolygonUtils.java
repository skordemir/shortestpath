package com.havelsan.visgraph.sampler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author skordemir
 *
 */
public class PolygonUtils {

	private static final int polygonMaxVerts = 4;

	private static final int polygonMinVerts = 3;

	/**
	 * will create a polygon in the given coordinates
	 * 
	 * @param numPoints
	 * @param boundX
	 * @param boundY
	 * @return
	 */
	public static XPolygon createPolygon(int numPoints, int boundX, int boundY) {
		Point[] points = new Point[numPoints];
		for (int i = 0; i < numPoints; i++) {
			Point p = new Point(Math.random() * boundX, Math.random() * boundY);
			points[i] = p;
		}
		XPolygon path = new XPolygon();
		for (int i = 0; i < points.length; i++) {
			path.add(points[i]);
		}
		return path;
	}

	/**
	 * will assign vertex counts to polygons
	 * 
	 * @param numPolygons
	 * @param vertsPerPoly
	 * @param vertCount
	 */
	public static void assingRandomVertx(int numPolygons, Integer[] vertsPerPoly, Integer vertCount) {
		for (int i = 0; i < numPolygons; i++) {
			int numVerts = RandomUtil.getRandomInterval(polygonMinVerts, polygonMaxVerts);
			vertsPerPoly[i] = numVerts;
			vertCount += numVerts;
		}
	}

	public static void craeteVisibilityGraphs(List<Point[]> segments, List<Point> vertices) {

		// loop all lines
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = i + 1; j < vertices.size(); j++) {
				Point a = vertices.get(i);
				Point b = vertices.get(j);
				boolean intersect = checkTwoPointsIntersect(segments, a, b);
				if (!intersect) {
					a.addVisiblePoint(b);
					b.addVisiblePoint(a);
				}

			}
		}

	}

	private static boolean checkTwoPointsIntersect(List<Point[]> segments, Point a, Point b) {
		boolean intersect = false;
		for (Point[] pointArray : segments) {
			Point c = pointArray[0];
			Point d = pointArray[1];
			intersect = doIntersect(a, b, c, d);
			if(a.equals(c) || a.equals(d) || b.equals(d) || b.equals(c))
				intersect=false;
			if (intersect)
				return true;

		}
		return false;
	}

	public static boolean polygonIntersect(List<Point[]> realSegments, XPolygon p) {
		List<Point[]> segments = new ArrayList<>();
		createSegments(segments, p);
		for (Point[] pointArray : segments) {
			Point a = pointArray[0];
			Point b = pointArray[1];
			boolean intersect = checkTwoPointsIntersect(realSegments, a, b);
			if (intersect)
				return intersect;

		}
		return false;
	}

	/**
	 * will find all the segments of points
	 * 
	 * @param segments
	 * @param polygon
	 * @param vertIndex
	 * @return
	 */
	public static void createSegments(List<Point[]> segments, XPolygon polygon) {
		List<Point> points = polygon.getPoints();
		for (int i = 0; i < points.size(); i++) {
			int k = i + 1 == points.size() ? 0 : i + 1;
			int a = i;
			int b = k;
			Point[] pointArray = new Point[2];
			pointArray[0] = points.get(a);
			pointArray[1] = points.get(b);
			segments.add(pointArray);
		}

	}

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	private static boolean onSegment(Point p, Point q, Point r) {
		if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX())
				&& q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
			return true;

		return false;
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	private static int orientation(Point p, Point q, Point r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		int val = (int) ((q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY()));

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false; // Doesn't fall in any of the above cases
	}

	public static void main(String[] args) {
		Point p1 = new Point(1, 1);
		Point q1 = new Point(3, 7);
		Point p2 = new Point(4, 2);
		Point q2 = new Point(10, 8);

		if (doIntersect(p1, q1, p2, q2))
			System.out.println("Yes");
		else
			System.out.println("No");

		p1 = new Point(10, 1);
		q1 = new Point(0, 10);
		p2 = new Point(0, 0);
		q2 = new Point(10, 10);
		if (doIntersect(p1, q1, p2, q2))
			System.out.println("Yes");
		else
			System.out.println("No");

		p1 = new Point(-5, -5);
		q1 = new Point(0, 0);
		p2 = new Point(1, 1);
		q2 = new Point(10, 10);
		;
		if (doIntersect(p1, q1, p2, q2))
			System.out.println("Yes");
		else
			System.out.println("No");
	}

}
