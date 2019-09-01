package com.havelsan.visgraph.sampler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PolygonGui {
	  private JFrame mainMap;
	    private List<Polygon> polyList;

	    public PolygonGui() {

	        initComponents();

	    }

	    private void initComponents() {

	        mainMap = new JFrame();
	        mainMap.setResizable(true);

	        mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        polyList = new ArrayList<Polygon>();
	        
	    	ShortestPath path = new ShortestPath();
		/*	Point startingPoint = new Point(23, 23);
			Point endPoint = new Point(129, 487);*/
			List<XPolygon>  polygonList= path.getRandomPolygons(800 );
			for (XPolygon polygon : polygonList) {
				int size = polygon.getPoints().size();
				int xPoly[] = new int[size];
				int yPoly[] = new int[size];
				List<Point> points = polygon.getPoints();
				for (int i=0 ;i<size;i++) {
					Point p = points.get(i);
					xPoly[i] = (int) p.getX();
					yPoly[i] = (int ) p.getY();
					
				}
				
				Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
				polyList.add(poly);

			}

	        JPanel p = new JPanel() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.setColor(Color.BLUE);
	                for (int i = 0; i < polyList.size(); i++) {
	                	Polygon poly = polyList.get(i);
	                	 g.drawPolygon(poly);
					}
	               
	            }

	            @Override
	            public Dimension getPreferredSize() {
	                return new Dimension(800, 600);
	            }
	        };
	        mainMap.add(p);
	        mainMap.pack();
	        mainMap.setVisible(true);

	    }

	    /**
	     * @param args
	     */
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new PolygonGui();
	            }
	        });
	    }

}
