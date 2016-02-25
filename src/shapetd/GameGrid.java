package shapetd;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import shapetd.GameObjects.HiliteTowerOutlines.HiliteTowerOutline;
import shapetd.GameObjects.Towers.Tower;

public class GameGrid {
	// Dimension screen_dimensions;
	boolean[][] boolgrid;
	// GridDirections[][] pathgrid;
	Point2D.Double[][] pointgrid;
	Node[][] pathgrid;

	/*
	 * double grid_square_width; double grid_square_spacing; int
	 * num_grid_squares;
	 */

	
	public static final double grid_square_width = 26.0;//screen_dimensions.height
			//* (3 + (1.0 / 3.0)) * 0.01;
	public static final double grid_square_spacing = grid_square_width * 0.32;
			//screen_dimensions.height * 0.008; // CHECK
																									// THIS
																									// NUMBER!!!!
	public static final int num_grid_squares = (int) (Math
			.round((Game.CANVAS_WIDTH - grid_square_spacing)
					/ (grid_square_width + grid_square_spacing)));

	public static enum GridDirections {
		NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST, BLOCKED, GOAL
	}

	public GameGrid() {
		/*
		 * //grid square is 6.667% height of screen this.grid_square_width =
		 * dimensions.height * (6 + (2.0/3.0)) * 0.005; this.grid_square_spacing
		 * = dimensions.height * (0.00952381)/2; //CHECK THIS NUMBER!!!!
		 * this.num_grid_squares =(int) (Math.round((dimensions.height -
		 * this.grid_square_spacing) / (this.grid_square_width +
		 * this.grid_square_spacing)));
		 */

		this.boolgrid = new boolean[this.num_grid_squares][this.num_grid_squares];
		makePointGrid();
		makePathGrid();
	}

	/*
	 * public int getNumGridSquares() { return this.num_grid_squares; }
	 * 
	 * public Dimension getScreenDimensions() { return this.screen_dimensions; }
	 */

	public boolean[][] getBooleanGrid() {
		return this.boolgrid;
	}

	/*
	 * public GridDirections[][] getPathGrid() { return this.pathgrid; }
	 */

	public Node[][] getPathGrid() {
		return this.pathgrid;
	}

	public Point2D.Double[][] getPointGrid() {
		return this.pointgrid;
	}

	// Only need to call once
	// assumes start with empty map
	public void makePathGrid() {
		/*
		 * GridDirections[][] grid = new
		 * GridDirections[this.num_grid_squares][this.num_grid_squares]; for(int
		 * i=0; i<this.num_grid_squares; i++) { for(int j=0;
		 * j<this.num_grid_squares; j++) { grid[i][j] = GridDirections.SOUTH; }
		 * } this.pathgrid = grid;
		 */

		Node[][] grid = new Node[num_grid_squares][num_grid_squares];
		for (int i = 0; i < num_grid_squares; i++) {
			for (int j = 0; j < num_grid_squares; j++) {
				Node newnode = new Node(this.pointgrid[i][j], new Point(i, j));
				grid[i][j] = newnode;
				if (j > 0) {
					grid[i][j - 1].setNext(newnode, GridDirections.SOUTH);
					grid[i][j].setPrev(grid[i][j - 1]);
				}

				if (j == num_grid_squares - 1) {
					grid[i][j].setDirection(GridDirections.GOAL);
				}
			}
		}
		
		//sets neighbors for each grid square node
		for (int i=0; i<num_grid_squares; i++) {
			for(int j=0; j<num_grid_squares; j++) {
				grid[i][j].setNeighbors(grid);
			}
		}
		this.pathgrid = grid;
	}

	// Only need to call once
	public void makePointGrid() {
		Point2D.Double[][] grid = new Point2D.Double[num_grid_squares][num_grid_squares];
		double x, y;
		for (int i = 0; i < num_grid_squares; i++) {
			for (int j = 0; j < num_grid_squares; j++) {
				x = grid_square_spacing + (grid_square_width / 2)
						+ (i * (grid_square_width + grid_square_spacing));
				y = grid_square_spacing + (grid_square_width / 2)
						+ (j * (grid_square_width + grid_square_spacing));
				grid[i][j] = new Point2D.Double(x, y);
			}
		}
		this.pointgrid = grid;
	}

	// Can be Optimized by using a binary search or mergesort type search
	public Point2D.Double findDestination(double x, double y) {
		double newx, newy, min, dist;
		Point2D.Double minpoint, point;
		min = Game.CANVAS_HEIGHT;
		minpoint = null;
		for (int i = 0; i < num_grid_squares; i++) {
			for (int j = 0; j < num_grid_squares; j++) {
				point = this.pointgrid[i][j];
				newx = point.x;
				newy = point.y;
				dist = Math.sqrt(Math.pow((newy - y), 2)
						+ Math.pow((newx - x), 2));
				if (dist <= min) {
					min = dist;
					minpoint = point;
				}
			}
		}
		return minpoint;
	}

	public Point findDestinationIndex(Point2D.Double point) {
		Point2D.Double p;	
		for (int i = 0; i < num_grid_squares; i++) {
			for (int j = 0; j < num_grid_squares; j++) {
				
				p = this.pointgrid[i][j];
				if ((double) (Math.abs(point.distance(p))) < 0.1 ) {
					return new Point(i, j);
				}
				
			}
		}
		return null;
	}
	
	public void unblockTowerSpace(Tower tower) {
		Point[] points = tower.getGridSquareIndexes();
		
		for(Point p : points) {
			pathgrid[p.x][p.y].setDirection(null);
		}
	}

	public void updatePaths(List<Tower> towers) {
		// USE DIJKSTRA'S TO FIND SHORTEST PATH FROM EVERY POINT ON GRID
		// SET LINKED LIST NEXT DIRECTION BASED ON SHORTEST PATH

		/*
		 * Node[][] grid = new Node[num_grid_squares][num_grid_squares];
		 * 
		 * //populate grid with new nodes for(int i=0; i<num_grid_squares; i++)
		 * { for(int j=0; j<num_grid_squares; j++) { grid[i][j] = new
		 * Node(this.pointgrid[i][j]);
		 * 
		 * //Set distance of the first row of grid squares to zero for path
		 * finding if(j == 0) { grid[i][j].setDistance(0.0); } } }
		 */
		
		//System.out.println("Updating paths");
		Node[][] grid = this.pathgrid;
		for (int i = 0; i < num_grid_squares; i++) {
			for(int j=0; j < num_grid_squares; j++) {
				grid[i][j].setDistance(Math.pow(Game.CANVAS_HEIGHT, 2) + 1);
				grid[i][j].setNext(null);
				grid[i][j].setPrev(null);
			}
		}

		// iterate over towers and set corresponding grid squares as BLOCKED
		for (Tower tower : towers) {
			Point[] indexes = tower.getGridSquareIndexes();
			// Grid squares with towers on them are Blocked for path grid
			for (Point index : indexes) {
				grid[index.x][index.y].setDirection(GridDirections.BLOCKED);
			}
		}

		// Run Dijkstras algorithm to find shortest path for all squares on grid
		grid = shortestPathsDijkstra(grid);
		this.pathgrid = grid;
		if(Game.TEST_MODE) printGrid();
		Game.getInstance().pathsUpdated();
	}
	
	
	private Node[][] shortestPathsDijkstra(Node[][] grid) {
		
		Node curNode;
		DistanceNode curDistNode, neighborDistNode;
		DistanceNode [][] distances = new DistanceNode[num_grid_squares][num_grid_squares];
		
		Comparator<DistanceNode> comparator = new NodeDistanceComparator();
		PriorityQueue<DistanceNode> pqueue = new PriorityQueue<DistanceNode>((int) Math.pow(
				num_grid_squares, 2) + 1, comparator);
		
		double dist, newdist;
		
		//change all distances back to max
		for(int k = 0; k < num_grid_squares; k++) {
		  for(int l = 0; l < num_grid_squares; l++) {
			  
			  if(grid[k][l].getDirection() == GridDirections.BLOCKED) {
				  continue;
			  }
			  
			  if(l == (num_grid_squares - 1)) {
				  distances[k][l] = new DistanceNode(0.0, new Point(k, l));
			  }
			  else {
				  distances[k][l] = new DistanceNode(Math.pow(Game.CANVAS_HEIGHT, 2) + 1, new Point(k, l));
			  }
			 
			  pqueue.add(distances[k][l]);
		  }
		}
		
					
		curNode = null;
		neighborDistNode = null;
		
		//dijkstras algorithm
		while (!pqueue.isEmpty()) {
			
			curDistNode = pqueue.remove();		
			if(curDistNode.visited()) continue;
			curDistNode.visit();
			
			curNode = grid[curDistNode.getIndexes().x][curDistNode.getIndexes().y];
			
			//goal test	
			if(curNode.getIndexes().y == 0) {
				continue;
			}
			
			dist = curDistNode.getDistance();
			Point2D.Double location = curNode.getLocation();		
			
			
			for(Node neighbor : curNode.getNeighbors()) {
				
				//cant route to a blocked node or a node that has already found its shortest path
				if(neighbor.routeBlocked(curNode)) {
					continue;
				}
				
				neighborDistNode = distances[neighbor.getIndexes().x][neighbor.getIndexes().y];
				if(neighborDistNode.visited()) continue;
				
				newdist = location.distance(neighbor.getLocation());
				
				
				if((dist + newdist) < neighborDistNode.getDistance()) {
					
					pqueue.remove(neighborDistNode);
					neighborDistNode.setDistance(dist + newdist);
					pqueue.add(neighborDistNode);
					
					neighbor.setNext(curNode);
					neighbor.setDirection(getDirection(curNode.getIndexes().x - neighbor.getIndexes().x, 
							curNode.getIndexes().y - neighbor.getIndexes().y));
					
				}
			}
		}	
		
		return grid;
	}

	
	public static GridDirections getDirection(int x, int y) {
		switch (x) {
		case -1:
			switch (y) {
			case -1:
				return GridDirections.NORTHWEST;
			case 0:
				return GridDirections.WEST;
			case 1:
				return GridDirections.SOUTHWEST;
			}
		case 0:
			switch (y) {
			case -1:
				return GridDirections.NORTH;
			case 0:
				return GridDirections.GOAL;
			case 1:
				return GridDirections.SOUTH;
			}
		case 1:
			switch (y) {
			case -1:
				return GridDirections.NORTHEAST;
			case 0:
				return GridDirections.EAST;
			case 1:
				return GridDirections.SOUTHEAST;
			}
		default:
			return GridDirections.BLOCKED;
		}

	}
	
	public String getPrintableGridDirection(int x, int y) {
		Node node = pathgrid[x][y];
		GridDirections dir = node.getDirection();
		
		if(dir == GridDirections.BLOCKED) {
			return "#";
		}else if(node.getNext() == null) {
			return "?";
		}else if(dir == GridDirections.EAST){
			return "E";
		}else if(dir == GridDirections.WEST) {
			return "W";
		}else if(dir == GridDirections.SOUTH) {
			return "S";
		}else if(dir == GridDirections.NORTH) {
			return "N";
		}else if(dir == GridDirections.GOAL) {
			return "G";
		}
		else {
			return " ";
		}
	}
	
	public void printGrid() {
		for(int i = 0; i < num_grid_squares; i++) {
			
			String line = "|";
			for(int j = 0; j < num_grid_squares; j++) {
				line += getPrintableGridDirection(j, i);
			}
			
			line += "|";
			System.out.println(line);
		}
	}
	
	//hilite the four squares that represent where the new tower is to be placed
	//mouse location is the top left corner of tower placement
	public HiliteTowerOutline hiliteTowerSquare(double x, double y, HiliteTowerOutline outline) {
		Point2D.Double nodepoint = findDestination(x, y);
		Point index = findDestinationIndex(nodepoint);
		Node[] squares = getTowerSquares(index);
		Point2D.Double hilitePoint = getUpperLeftFromCenter(squares[0].getLocation());
		
		if(squares[0].getIndexes().y <= 1) return outline;
		
		outline.setLocation(hilitePoint);
		outline.setSquares(squares);
		outline.updateGraphic();
		return outline;
	}
	
	public Point2D.Double getUpperLeftFromCenter(Point2D.Double point) {
		double shift = grid_square_width / 2;
		return new Point2D.Double(point.x-shift, point.y-shift);
	}
	
	public Node[] getTowerSquares(Point p) {
		Node[] squares = new Node[4];
		int x, y;
		x = p.x; y = p.y;
		
		if(x == (num_grid_squares - 1) && y == (num_grid_squares - 1)) {
			squares[0] = pathgrid[x-1][y-1];
			squares[1] = pathgrid[x-1][y];
			squares[2] = pathgrid[x][y-1];
			squares[3] = pathgrid[x][y];
		}
		else if(x == (num_grid_squares - 1)) {
			squares[0] = pathgrid[x-1][y];
			squares[1] = pathgrid[x-1][y+1];
			squares[2] = pathgrid[x][y];
			squares[3] = pathgrid[x][y+1];
		} else if(y == (num_grid_squares - 1)) {
			squares[0] = pathgrid[x][y-1];
			squares[1] = pathgrid[x][y];
			squares[2] = pathgrid[x+1][y-1];
			squares[3] = pathgrid[x+1][y];	
		} else {
			squares[0] = pathgrid[x][y];
			squares[1] = pathgrid[x][y+1];
			squares[2] = pathgrid[x+1][y];
			squares[3] = pathgrid[x+1][y+1];
		}
		return squares;
	}

}
