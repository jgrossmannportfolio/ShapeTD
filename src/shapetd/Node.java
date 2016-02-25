package shapetd;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import shapetd.GameGrid.GridDirections;

public class Node {
	Point2D.Double location;
	Node next;
	Node prev;
	GridDirections direction;
	double distance;
	Point indexes;
	List<Node> neighbors;

	public Node(Point2D.Double location, Node next, Node prev,
			GridDirections direction, Point indexes) {
		this.location = location;
		this.next = next;
		this.prev = prev;
		this.direction = direction;
		this.distance = Math.pow(Game.CANVAS_HEIGHT, 2) + 1;
		this.indexes = indexes;
		this.neighbors = new ArrayList<Node>();
	}

	public Node(Point2D.Double location, Point indexes) {
		this(location, null, null, null, indexes);
	}

	public Node getNext() {
		return this.next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void setNext(Node next, GridDirections direction) {
		setNext(next);
		setDirection(direction);
	}

	public Node getPrev() {
		return this.prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Point2D.Double getLocation() {
		return this.location;
	}

	public void setLocation(Point2D.Double location) {
		this.location = location;
	}

	public GridDirections getDirection() {
		return this.direction;
	}

	public void setDirection(GridDirections direction) {
		this.direction = direction;
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
	
	public List<Node> getNeighbors() {
		return this.neighbors;
	}
	public void setNeighbors(Node[][] grid) {
		int x,y;
		x = this.indexes.x; y = this.indexes.y;
		for(int i=-1; i<2; i++) {
			for(int j=-1; j<2; j++) {
				if(((x+i) < 0) || ((x+i) >= GameGrid.num_grid_squares) || ((y+j) < 0) || ((y+j) >= GameGrid.num_grid_squares)) {
					continue;
				}
				if((i == 0) && (j == 0)) {
					continue;
				}
				neighbors.add(grid[x+i][y+j]);
			}
		}
	}
	
	public boolean routeBlocked(Node nextNode) {
		
		if(!neighbors.contains(nextNode)) return false;
		if(direction == GridDirections.BLOCKED || nextNode.getDirection() == GridDirections.BLOCKED) return true;
		
		GridDirections directionTo = GameGrid.getDirection(nextNode.getIndexes().x - indexes.x, nextNode.getIndexes().y - indexes.y);
		
		//check diagonal cases
		if(directionTo == GridDirections.NORTHEAST) {
			boolean north = false, east = false;
			for(Node neighbor : neighbors) {
				if((neighbor.getIndexes().y < indexes.y) && (neighbor.getIndexes().x == indexes.x)) {
					north = (neighbor.getDirection() == GridDirections.BLOCKED);
				}else if((neighbor.getIndexes().x > indexes.x) && (neighbor.getIndexes().y == indexes.y)) {
					east = (neighbor.getDirection() == GridDirections.BLOCKED);
				}
			}
			
			return east && north;
		}
		else if(directionTo == GridDirections.NORTHWEST) {
			boolean north = false, west = false;
			for(Node neighbor : neighbors) {
				if((neighbor.getIndexes().y < indexes.y) && (neighbor.getIndexes().x == indexes.x)) {
					north = (neighbor.getDirection() == GridDirections.BLOCKED);
				}else if((neighbor.getIndexes().x < indexes.x) && (neighbor.getIndexes().y == indexes.y)) {
					west = (neighbor.getDirection() == GridDirections.BLOCKED);
				}
			}
			
			return west && north;
		}
		else if(directionTo == GridDirections.SOUTHEAST) {
			boolean south = false, east = false;
			for(Node neighbor : neighbors) {
				if((neighbor.getIndexes().y > indexes.y) && (neighbor.getIndexes().x == indexes.x)) {
					south = (neighbor.getDirection() == GridDirections.BLOCKED);
				}else if((neighbor.getIndexes().x > indexes.x) && (neighbor.getIndexes().y == indexes.y)) {
					east = (neighbor.getDirection() == GridDirections.BLOCKED);
				}
			}
			
			return east && south;
		}
		else if(directionTo == GridDirections.SOUTHWEST) {
			boolean south = false, west = false;
			for(Node neighbor : neighbors) {
				if((neighbor.getIndexes().y > indexes.y) && (neighbor.getIndexes().x == indexes.x)) {
					south = (neighbor.getDirection() == GridDirections.BLOCKED);
				}else if((neighbor.getIndexes().x < indexes.x) && (neighbor.getIndexes().y == indexes.y)) {
					west = (neighbor.getDirection() == GridDirections.BLOCKED);
				}
			}
			
			return west && south;
		}
			
		return false;
	}
}
