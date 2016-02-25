package shapetd.GameObjects.Enemies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import shapetd.GameObjects.GameObject.Attributes;

public class TriangleEnemy extends Enemy{
	
	public static final int WIDTH = 25;
		
	public static final int BASE_HEALTH = 45;
	
	public static final double BASE_SPEED = 1.36;

	public TriangleEnemy(Shape graphic, double speed, int health, int armor,
			Attributes[] resistances, int level, Point2D.Double destination,
			Point destindex) {
		
		super(graphic, speed, health, armor, resistances, level, destination,
				destindex);

	}

	public TriangleEnemy(double x, double y, Point2D.Double destination,
			Point destindex, int level) {
		
		this(createTriangle(x, y), calculateSpeed(level), calculateHealth(level), 0, new Attributes[0],
					level, destination, destindex);
	}
	
	public static int calculateHealth(int level) {
		int health = BASE_HEALTH;
		for(int i = 1; i<level; i++) {
			health *= 1.9;
		}
		return health;
	}
	
	public String getType() {
		return "Triangle Enemy";
	}
	
	private static double calculateSpeed(int level) {
		double speed = BASE_SPEED;
		for(int i = 1; i<level; i++) {
			speed *= 1.075;
		}
		return speed;
	}
	
	private static Shape createTriangle(double x, double y) {
		int width = WIDTH / 2;
		
		Path2D triangle = new Path2D.Double();
		triangle.moveTo(x + width, y);
		triangle.lineTo(x + width * 2.0, y + (width * 2.0));
		triangle.lineTo(x, y + (width * 2.0));
		triangle.closePath();
		
		return triangle;
	}


	public void updateGraphic(double x, double y) {
		this.setGraphic(createTriangle(x, y));
	}
	

	@Override
	public int getReward() {
		if(level < 6) {
			return (int) ((level - 1)/ 1.5) + 1;
		}else {
			return (int) Math.floor(level * 1.5);
		}
	}
}
