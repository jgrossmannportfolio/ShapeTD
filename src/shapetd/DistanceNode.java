package shapetd;

import java.awt.Point;

public class DistanceNode {
	private double distance;
	private Point indexes;
	private boolean visited;
	
	public DistanceNode(double distance, Point indexes) {
		this.distance = distance;
		this.indexes = indexes;
		visited = false;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Point getIndexes() {
		return this.indexes;
	}
	
	public void setIndexes(Point indexes) {
		this.indexes = indexes;
	}
	
	public void visit() {
		visited = true;
	}
	
	public boolean visited() {
		return visited;
	}
	
	public void unVisit() {
		visited = false;
	}

}
